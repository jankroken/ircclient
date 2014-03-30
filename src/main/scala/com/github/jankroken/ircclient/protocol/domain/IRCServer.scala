package com.github.jankroken.ircclient.protocol.domain

import java.net.Socket

import com.github.jankroken.ircclient.protocol.{ClientMessage, IRCMessageService}

class IRCServer(val name: String, val port: Int) {
	
	require (port > 0)
	require (port < 64000)
  
	var channels = Map[String,Channel]()
 
	def this(name: String) = this(name, 6667)

	var user: User = null
	var welcomeMessage: Option[WelcomeMessage] = None
	var messageOfTheDay: Option[MessageOfTheDay] = None
	
	private var messageService: IRCMessageService = null
		
	def connect(optionalOnMessage:Option[(ServerMessage) ⇒ Unit]) = {
		val socket = new Socket(name,port)
		def defaultOnMessage(message: ServerMessage) {
				message match {
				  case Topic(channel, string) ⇒
				  	  channel.setTopic(string)
				  case TopicSetBy(channel, user, timestamp) ⇒
				  	  channel.setTopicSetter(user)
				  	  channel.setTopicTime(timestamp)
				  case Authorization(info) ⇒
				  	  println(IRCServer.this+": "+message)
				  case Join(origin,channel) ⇒
				  	  channel.userJoined(origin)
				  case Ping(server) ⇒
				  	  println(message)
				  	  pong(server)
				  case _ ⇒ println("SERVER: "+message)
				}
		}
    val onMessage = optionalOnMessage match { case Some(onMsg) ⇒ onMsg case None ⇒ defaultOnMessage(_) }
		val messageConverter: MessageConverter = new MessageConverter(onMessage, this)
		messageService = new IRCMessageService(socket, messageConverter)
		messageService.start()
	}
	
	def getMessageService = messageService

	def logon = {
		messageService.sendMessage(ClientMessage.user(user))
	}
	
	def pong(server: String) {
		messageService.sendMessage(ClientMessage.pong(server))
	}
 
	private def pong(server: String, server2: String) {
		messageService.sendMessage(ClientMessage.pong(server,server2))
   }
 
	def setNick(nick: String) {
		messageService.sendMessage(ClientMessage.nick(nick))
	}
	
	def message(channel: Channel, message: String) {
		messageService.sendMessage(ClientMessage.privmsg(channel, message))
	}
	
	def quit(message: String) {
		messageService.sendMessage(ClientMessage.quit(message))
	}
	
	def join(name: String):Channel = {
		val channel = getChannel(name) 
		messageService.sendMessage(ClientMessage.join(channel))
		channel
	}

	def getChannel(name: String):Channel =
		channels.get(name) match {
		  case Some(channel) ⇒ channel
		  case None ⇒ {
        val channel = new Channel(name,this)
        channels = channels + (name → channel)
        channel
      }
    }

	override def toString = "IRCServer"
}
