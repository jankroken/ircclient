package com.googlecode.estuary.sirc.domain

class MessageOfTheDay extends ServerMessage {
	private var lines: List[String] = List[String]()
	
	def addLine(line: String) {
		lines = lines ::: List(line)
	}
	
	override def toString():String = {
		val sb = new StringBuilder()
		sb.append("MessageOfTheDay {\n")
		for (line <- lines) {
			sb.append("  ").append(line).append('\n')
		}
		sb.append("}")
		return sb.toString()
	}
}