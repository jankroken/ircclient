package com.github.jankroken.ircclient.protocol.domain

abstract class ServerMessage
case class Authorization(val message: String) extends ServerMessage
case class ChannelCount(val count: Int) extends ServerMessage
case class EndOfNames(channel: Channel) extends ServerMessage
case class Error(message:String) extends ServerMessage
case class Ping(servername: String) extends ServerMessage
case class NickAlreadyInUse(nick: Nick) extends ServerMessage
case class NotRegistered(args: Array[String]) extends ServerMessage
case class OperatorCount(count: String) extends ServerMessage
case class UnknownConnectionCount(count: String) extends ServerMessage

case class ChangeNick(origin: Option[Origin], newNick: String) extends ServerMessage
