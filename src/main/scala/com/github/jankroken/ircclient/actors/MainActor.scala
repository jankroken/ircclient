package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}

class MainActor(server:String) extends Actor with ActorLogging {
  val freenode = IRCActorSystem.system.actorOf(Props[NetworkActor], name = "freenode")

  def receive = {
      case foo ⇒ {
        println("NetworkActor: $foo")
        freenode ! foo
    }
  }
}
