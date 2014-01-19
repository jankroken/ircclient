package com.googlecode.estuary.sirc.domain

class WelcomeMessage extends ServerMessage {

	var lines = new Array[String](4)

	def setLine(lineNo: Int, message: String) {
		lines(lineNo-1) = message
	}

	def apply(lineNo: Int, message: String) {
	  setLine(lineNo, message)
  }
 
	def getLines():Array[String] = lines
	
	override def toString():String = {
    val sb: StringBuilder = new StringBuilder()
		sb.append("WelcomeMessage {\n")
		for (line <- lines) {
			sb.append("  ").append(line).append('\n')
		}
		sb.append('}')
		sb.toString()
	}
}