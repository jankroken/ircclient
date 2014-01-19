package com.googlecode.estuary.sirc.domain

case class Topic(channel: Channel, topic: String) extends ServerMessage {
  override def toString:String = {
    "<Topic("+channel+") "+topic+">"
  }
}
