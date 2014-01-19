package com.github.jankroken.ircclient.protocol.domain

case class Join(origin: Option[Origin], channel: Channel) extends ServerMessage {
	override def toString:String = {
	   val originString:String = origin match {
	     case Some(origin) => "("+origin+")"
	     case None => ""
	   }
       return originString+" Join("+channel+")"
	}
}
