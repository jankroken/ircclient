package com.github.jankroken.ircclient.protocol

import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket


import com.github.jankroken.ircclient.protocol.domain.LowLevelMessageListener

class IRCMessageService(socket: Socket, messageListener: LowLevelMessageListener) {
	
	val input:BufferedReader = 
		new BufferedReader(new InputStreamReader(socket.getInputStream()))
	val	output:PrintWriter = new PrintWriter(socket.getOutputStream())

	def sendMessage(message: ClientMessage)
	{
		println("Writing: "+message.commandString)
		output.print(message.commandString)
		output.print("\r\n")
		output.flush()
	}
	
	def start() = {
		println("Using messageListener: "+messageListener)
		new MessageReceiver(input, messageListener)
	}
	
}
