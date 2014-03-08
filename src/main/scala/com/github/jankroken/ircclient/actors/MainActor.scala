package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.ChatPanels

class MainActor(server:String) extends Actor with ActorLogging {

//  val freenode = IRCActorSystem.system.actorOf(Props[NetworkActor].withDispatcher("javafx-dispatcher"), name = "freenode")


  val freenode = IRCActorSystem.system.actorOf(Props(new NetworkActor("freenode")).withDispatcher("javafx-dispatcher"))

  def receive = {
    case chatPanels:ChatPanels ⇒ {
      freenode ! chatPanels
    }
    case foo ⇒ {
      println(s"NetworkActor: $foo")
      freenode ! foo
    }
  }
}