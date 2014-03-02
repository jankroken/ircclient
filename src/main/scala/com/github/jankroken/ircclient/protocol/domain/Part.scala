package com.github.jankroken.ircclient.protocol.domain

case class Part(origin: Option[Origin], val channel: Channel, reason: String) extends ServerMessage {

  override def toString: String = {
    val originString: String = origin match {
      case Some(origin) ⇒ "(" + origin + ")"
      case None ⇒ ""
    }
    val reasonString = if ("".equals(reason)) "" else " " + reason
    s"$originString Part($channel$reasonString)"
  }
}
