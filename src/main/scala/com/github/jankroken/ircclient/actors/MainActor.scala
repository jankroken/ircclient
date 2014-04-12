package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.{ChannelPane, NickPanes, ChatPanels}
import com.github.jankroken.ircclient.domain.Init
import com.github.jankroken.ircclient.commands.{Command, IdentifiedCommand}

class MainActor(server:String) extends Actor with ActorLogging {

  val gui = IRCActorSystem.system.actorOf(Props(new GUIActor("freenode")).withDispatcher("javafx-dispatcher"),"gui")
  val freenode = IRCActorSystem.system.actorOf(Props(new NetworkActor(gui,"freenode","irc.freenode.net")),"freenode") //.withDispatcher("javafx-dispatcher"),"freenode")
  val target = IRCActorSystem.system.actorOf(Props(new TargetActor), name = "activeTarget")
  val script = IRCActorSystem.system.actorOf(Props(new ScriptActor), name = "script")

  freenode ! Init
  script ! "init"
  script ! "unload"
  script ! "init"

  def receive = {
    case chatPanels:ChatPanels ⇒
      gui ! chatPanels
    case nickPanels:NickPanes ⇒
      gui ! nickPanels
    case channelPane:ChannelPane ⇒
      gui ! channelPane
    case text:IdentifiedCommand.Text ⇒
      target ! text
    case join:IdentifiedCommand.Join ⇒
      println(s"MainActor:join: $join")
      target ! join
    case command:Command ⇒
      println(s"MainActor:command: $command")
      freenode ! command
    case other ⇒
      println(s"MainActor: $other")
      target ! other
      freenode ! other
  }
}