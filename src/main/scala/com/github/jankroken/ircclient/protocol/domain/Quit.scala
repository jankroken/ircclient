package com.github.jankroken.ircclient.protocol.domain

case class Quit(origin: Option[Origin], reason: String) extends ServerMessage {
	override def toString = {
	   val originString = origin match {
	     case Some(origin) => "("+origin+")"
	     case None => ""
	   }
     originString+" Quit("+reason+")"
	}
}
