package com.github.jankroken.ircclient.commands

sealed class IdentifiedCommand {
  case class Join(params:String)
  case class Server(params:String)
  case class Nick(params:String)
}