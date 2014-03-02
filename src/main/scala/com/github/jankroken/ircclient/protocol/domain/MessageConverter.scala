package com.github.jankroken.ircclient.protocol.domain

import com.github.jankroken.ircclient.protocol.LowLevelServerMessage

class MessageConverter(onMessage: (ServerMessage) ⇒ Unit, val server: IRCServer) extends LowLevelMessageListener {

  private val welcomeMessage = new WelcomeMessage()
  private var motd: MessageOfTheDay = null

  def onMessage(message: LowLevelServerMessage) {
    message.command match {
      case "001" ⇒ welcomeMessage.setLine(1, message.arguments(1))
      case "002" ⇒ welcomeMessage.setLine(2, message.arguments(1))
      case "003" ⇒ welcomeMessage.setLine(3, message.arguments(1))
      case "004" ⇒
        welcomeMessage.setLine(4, message.arguments(1))
        onMessage(welcomeMessage)
      case "005" ⇒
        onMessage(ServerParameters(message.arguments))
      case "252" ⇒
        onMessage(OperatorCount(message.arguments(1)))
      case "253" ⇒
        onMessage(UnknownConnectionCount(message.arguments(1)))
      case "254" ⇒ onMessage(ChannelCount((message.arguments(1)).toInt))
      case "255" ⇒ onMessage(new ClientServerCount(message.arguments(1)))
      case "332" ⇒
        onMessage(Topic(server.getChannel(message.arguments(1)),
          message.arguments(2)))
      case "333" ⇒
        onMessage(TopicSetBy(server.getChannel(message.arguments(1)),
          message.arguments(2),
          message.arguments(3)))
      case "353" ⇒
        onMessage(NameList(message.arguments(1),
          server.getChannel(message.arguments(2)),
          message.arguments(3)))
      case "366" ⇒
        onMessage(EndOfNames(server.getChannel(message.arguments(1))))
      case "375" ⇒
        motd = new MessageOfTheDay()
        motd.addLine(message.arguments(1))
      case "372" ⇒ motd.addLine(message.arguments(1))
      case "376" ⇒ onMessage(motd)
      case "433" ⇒
        onMessage(new NickAlreadyInUse(new Nick(message.arguments(1))))
      case "451" ⇒
        onMessage(new NotRegistered(message.arguments))
      case "ERROR" ⇒ onMessage(new Error(message.arguments(0)))
      case "JOIN" ⇒ onMessage(new Join(message.origin, server.getChannel(message.arguments(0))))
      case "NICK" ⇒ onMessage(new ChangeNick(message.origin, message.arguments(0)))
      case "NOTICE" ⇒
        val targets = Target.getTargets(message.arguments(0), server)
        val isAuth = targets(0) match {
          case Nick(n) ⇒ "AUTH".equals(n)
          case _ ⇒ false
        }
        if (isAuth) {
          onMessage(new Authorization(message.arguments(1)))
        } else {
          onMessage(new Notice(message.origin, targets, message.arguments(1)))
        }

      case "PART" ⇒
        val channel = server.getChannel(message.arguments(0))
        channel.userLeft(message.origin)
      //			  messageListener.onMessage(new Part(message.origin,
      //                                                   server.getChannel(message.arguments(0)),
      //                                                   message.arguments(1)))
      case "PING" ⇒
        onMessage(new Ping(message.arguments(0)))
      case "QUIT" ⇒ onMessage(new Quit(message.origin, message.arguments(0)))
      case "PRIVMSG" ⇒
        val targets = Target.getTargets(message.arguments(0), server)
        onMessage(new PrivateMessage(message.origin, targets, message.arguments(1)))
      case _ ⇒ onMessage(Unidentified(message))
    }
  }

  override def toString = "IRCServer"
}
