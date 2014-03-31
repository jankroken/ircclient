package com.github.jankroken.ircclient.commands

import com.github.jankroken.ircclient.commands.IdentificationParser.{ServerCommand, JoinCommand, TextCommand}

sealed class IdentifiedCommand

object IdentifiedCommand {
  case class Join(channel:String) extends IdentifiedCommand
  case class Server(server:String) extends IdentifiedCommand
  case class Nick(params:String) extends IdentifiedCommand
  case class Text(param:String) extends IdentifiedCommand
  case class Unknown(o:Object) extends IdentifiedCommand
  def from(o:Object) = o match {
    case IdentificationParser.TextCommand(line) ⇒ Text(line)
    case IdentificationParser.JoinCommand(channel) ⇒ Join(channel)
    case IdentificationParser.ServerCommand(server) ⇒ Server(server)
    case other ⇒ Unknown(other)
  }
}