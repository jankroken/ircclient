package com.github.jankroken.ircclient.domain

abstract sealed class Context

object Context {
  case object TopLevel extends Context
  case class Server(val name:String) extends Context
  case class Channel(server:Server,name:String) extends Context
  case class User(server:Server,name:String) extends Context
}
