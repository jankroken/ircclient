package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.ChatPanels

class MainActor(server:String) extends Actor with ActorLogging {

  val freenode = IRCActorSystem.system.actorOf(Props[NetworkActor].withDispatcher("javafx-dispatcher"), name = "freenode")

  def receive = {
      case foo ⇒ {
        println("NetworkActor: $foo")
        freenode ! foo
      }
      case chatPanels:ChatPanels ⇒ {
        freenode ! chatPanels
      }
  }
}