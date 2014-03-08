package com.github.jankroken.ircclient.protocol.domain

class MessageOfTheDay extends ServerMessage {
	private var lines: List[String] = List[String]()
	
	def addLine(line: String) {
		lines = lines ::: List(line)
	}
	
	override def toString = {
		s"MessageOfTheDay{\n${lines.map(line => s"  $line").mkString("\n")}}"
	}
}
