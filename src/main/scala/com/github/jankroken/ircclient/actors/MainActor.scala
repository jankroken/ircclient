package com.github.jankroken.ircclient.actors

import akka.actor.{ActorRef, Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.{AddNetworkToTreeView, ChannelPane, NickPanes, ChatPanels}
import com.github.jankroken.ircclient.domain.Init
import com.github.jankroken.ircclient.commands._
import com.github.jankroken.ircclient.commands.TextCommand
import com.github.jankroken.ircclient.commands.Server

class MainActor extends Actor with ActorLogging {

  import IRCActorSystem.system.actorOf

  val gui = IRCActorSystem.system.actorOf(Props(new GUIActor).withDispatcher("javafx-dispatcher"),"gui")
  val target = IRCActorSystem.system.actorOf(Props(new TargetActor), name = "activeTarget")
  val script = IRCActorSystem.system.actorOf(Props(new ScriptActor), name = "script")

  script ! "init"
//  script ! "unload"
//  script ! "init"

  def receive = {
    case msg =>
      self ! msg
      context.become(receiveCommands(Map()))
  }

  def receiveCommands(networks: Map[String,ActorRef]):Receive = {
    case chatPanels:ChatPanels ⇒
      gui ! chatPanels
    case nickPanels:NickPanes ⇒
      gui ! nickPanels
    case channelPane:ChannelPane ⇒
      gui ! channelPane
    case identifiedCommand:IdentifiedCommand ⇒
        target ! identifiedCommand
    case Server(net) ⇒
      println("MainActor::server: $net")
      val networkActor = networks.get(net.name) match {
        case Some(serverActor) => serverActor
        case None => actorOf(Props(new NetworkActor(gui,net.name,net.name)),net.name)
      }
      val newNet = networks + (net.name -> networkActor)
      gui ! AddNetworkToTreeView(net)
      networkActor ! Init
      println(newNet)
      context.become(receiveCommands(newNet))
    case command:TextCommand ⇒
      println(s"MainActor:command: $command ${command.getClass}")
      networks(command.target.network) ! command
    case command:JoinCommand ⇒
      println(s"MainActor:command: $command ${command.getClass}")
      networks(command.target.network) ! command
    case other ⇒
      println(s"MainActor: $other ${other.getClass}")
      target ! other
      networks("irc.freenode.org") ! other
  }
}