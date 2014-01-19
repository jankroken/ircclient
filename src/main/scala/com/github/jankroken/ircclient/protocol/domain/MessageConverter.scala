package com.github.jankroken.ircclient.protocol.domain

import com.googlecode.estuary.sirc.protocol.LowLevelServerMessage

class MessageConverter(messageListener: MessageListener, val server: IRCServer) extends LowLevelMessageListener{

	private var welcomeMessage: WelcomeMessage = new WelcomeMessage()
	private var motd: MessageOfTheDay = null
	
	def onMessage(message: LowLevelServerMessage) {
		message.command match {
			case "001" => welcomeMessage.setLine(1, message.arguments(1))
			case "002" => welcomeMessage.setLine(2, message.arguments(1))
			case "003" => welcomeMessage.setLine(3, message.arguments(1))
			case "004" => 
			  	welcomeMessage.setLine(4, message.arguments(1))
				messageListener.onMessage(welcomeMessage)
			case "005" => 
			  	messageListener.onMessage(new ServerParameters(message.arguments))
			case "252" =>
			  	messageListener.onMessage(new OperatorCount(message.arguments(1)))
			case "253" =>
			  	messageListener.onMessage(new UnknownConnectionCount(message.arguments(1)))
			case "254" => messageListener.onMessage(new ChannelCount((message.arguments(1)).toInt))
			case "255" => messageListener.onMessage(new ClientServerCount(message.arguments(1)))
			case "332" => 
			  	messageListener.onMessage(new Topic(server.getChannel(message.arguments(1)), 
                                          			message.arguments(2)))
			case "333" =>
			  	messageListener.onMessage(new TopicSetBy(server.getChannel(message.arguments(1)),
                                               			 message.arguments(2),
                                               			 message.arguments(3)))
			case "353" =>
			    messageListener.onMessage(new NameList(message.arguments(1),
                                 					   server.getChannel(message.arguments(2)),
                                 					   message.arguments(3)))
			case "366" =>
			    messageListener.onMessage(new EndOfNames(server.getChannel(message.arguments(1))))
			case "375" => 
			  	motd = new MessageOfTheDay()
				motd.addLine(message.arguments(1))
			case "372" => motd.addLine(message.arguments(1))
			case "376" => messageListener.onMessage(motd)
			case "433" =>
			  	messageListener.onMessage(new NickAlreadyInUse(new Nick(message.arguments(1))))
			case "451" =>
			  	messageListener.onMessage(new NotRegistered(message.arguments))
			case "ERROR" => messageListener.onMessage(new Error(message.arguments(0)))
			case "JOIN" => messageListener.onMessage(new Join(message.origin,server.getChannel(message.arguments(0))))
			case "NICK" => messageListener.onMessage(new ChangeNick(message.origin,message.arguments(0)))
			case "NOTICE" => 
				val targets = Target.getTargets(message.arguments(0),server)
				val isAuth = targets(0) match { 
				  case Nick(n) => "AUTH".equals(n)
				  case _ => false
				}
				if (isAuth) {
				  messageListener.onMessage(new Authorization(message.arguments(1)))
				} else {
				  messageListener.onMessage(new Notice(message.origin,targets,message.arguments(1)))
				}
				
			case "PART" =>
				val channel = server.getChannel(message.arguments(0))
				channel.userLeft(message.origin)
//			  messageListener.onMessage(new Part(message.origin,
//                                                   server.getChannel(message.arguments(0)),
//                                                   message.arguments(1)))
			case "PING" => 
				messageListener.onMessage(new Ping(message.arguments(0)))
			case "QUIT" => messageListener.onMessage(new Quit(message.origin,message.arguments(0)))
			case "PRIVMSG" =>
			  	val targets = Target.getTargets(message.arguments(0),server)
			  	messageListener.onMessage(new PrivateMessage(message.origin,targets,message.arguments(1))) 
			case _ => messageListener.onMessage(message)
		}
	}
 
	override def toString:String = "IRCServer"
}
