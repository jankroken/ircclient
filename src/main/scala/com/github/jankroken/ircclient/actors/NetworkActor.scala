package com.github.jankroken.ircclient.actors

import akka.actor.{Actor, ActorLogging}
import com.github.jankroken.ircclient.protocol.domain.{Channel, IRCServer, UserMode, User}

class NetworkActor(server:String) extends Actor with ActorLogging {

  val xeno = TestUser
  val freenode = new IRCServer("irc.freenode.org")
  freenode.user = xeno
  freenode.connect
  Thread.sleep(2000)
  freenode.setNick(xeno.xenobot7)
  freenode.logon
  Thread.sleep(4000)
  var fealdia: Channel = freenode.join("#fealdia")
  var digitalgunfire: Channel = freenode.join("#digitalgunfire")
  var scala: Channel = freenode.join("#scala")

  def receive = {
    case foo ⇒ {
      println("NetworkActor: $foo")
    }
  }
}
