package com.github.jankroken.ircclient.protocol.domain

import java.util.Date

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

case class Topic(channel: Channel, topic: String) extends ServerMessage {
  override def toString = "<Topic("+channel+") "+topic+">"
}

case class TopicSetBy(val channel: Channel, userString: String, timestampString: String) extends ServerMessage {
  val date = new Date
  override def toString = "TopicSetBy("+userString+","+timestampString+")"
}

case class ServerParameters(arguments: Array[String]) extends ServerMessage {
  override def toString:String = {
    val sb = new StringBuilder
    sb.append("ServerParameters(")
    for (argument <- arguments) {
      sb.append(" "+argument)
    }
    sb.append(")")
    sb.toString
  }
}

case class Quit(origin: Option[Origin], reason: String) extends ServerMessage {
  override def toString = {
    val originString = origin match {
      case Some(origin) => "("+origin+")"
      case None => ""
    }
    originString+" Quit("+reason+")"
  }
}

