package com.github.jankroken.ircclient.protocol.domain

case class Message(val text: String) extends ServerMessage {
	
	var target: Target = null
	
	override def toString = s"Message {(${null} → $target) : $text }"
}
