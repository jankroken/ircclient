package com.github.jankroken.ircclient.protocol.domain

abstract class ServerMessage
case class Authorization(val message: String) extends ServerMessage
case class ChannelCount(val count: Int) extends ServerMessage
case class EndOfNames(channel: Channel) extends ServerMessage

case class ChangeNick(origin: Option[Origin], newNick: String) extends ServerMessage
