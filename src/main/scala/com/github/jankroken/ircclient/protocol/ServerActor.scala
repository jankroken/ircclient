package com.github.jankroken.ircclient.protocol

import akka.actor.{Props, Actor}

class ServerActor(server:String,port:Int) extends Actor {
  def receive = {
    case _ ⇒ println(s"ServerActor($server):hello")
  }
}

object ServerActor {
  def apply(hostname:String,port:Int) = new ServerActor(hostname,port)

  def props(hostname:String,port:Int) = Props(classOf[ServerActor],hostname,port)
}
