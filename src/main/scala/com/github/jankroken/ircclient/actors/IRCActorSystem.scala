package com.github.jankroken.ircclient.actors

import akka.actor.{Props, ActorSystem}
import com.github.jankroken.ircclient.commands.Server
import com.github.jankroken.ircclient.domain.NetworkTarget

object IRCActorSystem {
  val system = ActorSystem("irc")
  val main = system.actorOf(Props(new MainActor), name = "main")
  main ! "hello"
  main ! Server(NetworkTarget("irc.freenode.org"))
}
