package com.googlecode.estuary.sirc.domain

case class Quit(origin: Option[Origin], reason: String) extends ServerMessage {
	override def toString:String = {
	   val originString:String = origin match {
	     case Some(origin) => "("+origin+")"
	     case None => ""
	   }
       return originString+" Quit("+reason+")"
	}
}
