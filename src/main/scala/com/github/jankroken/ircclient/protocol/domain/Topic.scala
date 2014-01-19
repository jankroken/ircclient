package com.github.jankroken.ircclient.protocol.domain

case class Topic(channel: Channel, topic: String) extends ServerMessage {
  override def toString:String = {
    "<Topic("+channel+") "+topic+">"
  }
}
