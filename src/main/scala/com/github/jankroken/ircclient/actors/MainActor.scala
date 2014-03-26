package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.{ChannelPane, NickPanes, ChatPanels}
import com.github.jankroken.ircclient.domain.Init

class MainActor(server:String) extends Actor with ActorLogging {

//  val freenode = IRCActorSystem.system.actorOf(Props[NetworkActor].withDispatcher("javafx-dispatcher"), name = "freenode")


  val gui = IRCActorSystem.system.actorOf(Props(new GUIActor("freenode")).withDispatcher("javafx-dispatcher"),"gui")
  val freenode = IRCActorSystem.system.actorOf(Props(new NetworkActor(gui,"freenode","irc.freenode.net")),"freenode") //.withDispatcher("javafx-dispatcher"),"freenode")

  freenode ! Init

  def receive = {
    case chatPanels:ChatPanels ⇒ {
      gui ! chatPanels
    }
    case nickPanels:NickPanes ⇒ {
      gui ! nickPanels
    }
    case channelPane:ChannelPane ⇒ {
      gui ! channelPane
    }
    case foo ⇒ {
      println(s"NetworkActor: $foo")
      freenode ! foo
    }
  }
}