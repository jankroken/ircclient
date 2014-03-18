package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.ChatPanels
import com.github.jankroken.ircclient.domain.Init

class MainActor(server:String) extends Actor with ActorLogging {

//  val freenode = IRCActorSystem.system.actorOf(Props[NetworkActor].withDispatcher("javafx-dispatcher"), name = "freenode")


  val gui = IRCActorSystem.system.actorOf(Props(new GUIActor("freenode")).withDispatcher("javafx-dispatcher"),"gui")
  val freenode = IRCActorSystem.system.actorOf(Props(new NetworkActor(gui,"freenode")),"freenode") //.withDispatcher("javafx-dispatcher"),"freenode")

  freenode ! Init

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