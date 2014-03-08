package com.github.jankroken.ircclient.actors

import akka.actor.{Props, ActorSystem}

object IRCActorSystem {
  val system = ActorSystem("irc")
  val main = IRCActorSystem.system.actorOf(Props(new MainActor("freenode")), name = "main")
  main ! "hello"
}
