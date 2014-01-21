package com.github.jankroken.ircclient.protocol.domain

case class Join(origin: Option[Origin], channel: Channel) extends ServerMessage {
	override def toString = {
	   val originString = origin match {
	     case Some(origin) => "("+origin+")"
	     case None => ""
	   }
     originString+" Join("+channel+")"
	}
}
