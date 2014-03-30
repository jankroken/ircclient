package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.{ChannelPane, NickPanes, ChatPanels}
import com.github.jankroken.ircclient.domain.Init
import com.github.jankroken.ircclient.commands.IdentifiedCommand

class MainActor(server:String) extends Actor with ActorLogging {

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
    case text:IdentifiedCommand.Text => {
      freenode ! text
    }
    case other ⇒ {
      println(s"MainActor: $other")
      freenode ! other
    }
  }
}