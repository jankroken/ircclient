package com.github.jankroken.ircclient.protocol

import akka.actor.{Props, ActorSystem}

object IRCService {
  private val system = ActorSystem("IRCService")
  def connect(servername:String,port:Int) = system.actorOf(ServerActor.props(servername,port),servername)
  def server(servername:String,port:Int) = connect(servername,port)

  def main(args:Array[String]) {
    server("irc.freenode.net",42) ! "hello"
  }
}

