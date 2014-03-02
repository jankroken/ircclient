package com.github.jankroken.ircclient.protocol.domain

case class PrivateMessage(origin: Option[Origin],targets: Array[Target],message:String) extends ServerMessage {
  
  override def toString = {
    val originString = origin match {
      case Some(origin) ⇒ s"($origin)"
      case None ⇒ ""
    }
    val targetString = targets.mkString(",")
    s"$originString<PrivateMessage $targetString} : $message>"
  }

}
