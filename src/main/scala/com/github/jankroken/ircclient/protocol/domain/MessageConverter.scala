package com.github.jankroken.ircclient.protocol.domain

import com.github.jankroken.ircclient.protocol.LowLevelServerMessage

class MessageConverter(onMessage: (ServerMessage) ⇒ Unit, val server: IRCServer) extends LowLevelMessageListener {

  private val welcomeMessage = new WelcomeMessage()
  private var motd: MessageOfTheDay = null

  def isCTCPMessage(message: LowLevelServerMessage) = {
    (message.command == "PRIVMSG" || message.command == "NOTICE") &&
    message.arguments.length > 1 &&
    message.arguments(1).charAt(0) == 1
  }

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
      case msg if isCTCPMessage(message) => onCTCPMessage(message)
      case "NOTICE" ⇒
//        if (message.arguments.length > 1 && message.arguments(1).charAt(0) == 1)
//          onCTCPMessage(message)
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
//        if (message.arguments.length > 1 && message.arguments(1).charAt(0) == 1)
//          onCTCPMessage(message)
        val targets = Target.getTargets(message.arguments(0), server)
        onMessage(new PrivateMessage(message.origin, targets, message.arguments(1)))
      case _ ⇒ onMessage(Unidentified(message))
    }
  }

  private def onCTCPMessage(message: LowLevelServerMessage) {
    def splitOnFirstSpace(s:String):(String,String) = {
      val firstSpace = s.indexOf(' ')
      if (firstSpace < 0) (s,"")
      else (s.substring(0,firstSpace),s.substring(firstSpace+1))
    }

    def removeFirstCharacter(s:String):String = s.substring(1)
    def removeLastCharacter(s:String):String = s.substring(0,s.length-1)
    def fixLastTokenOfArgumentsList(tokens:List[String]):List[String] = tokens match {
      case Nil => Nil
      case head::Nil => removeLastCharacter(head)::Nil
      case head::tail => head :: fixLastTokenOfArgumentsList(tail)
    }
    def fixTokens(tokens:List[String]):List[String] = tokens match {
      case head::tail => fixLastTokenOfArgumentsList(removeFirstCharacter(head)::tail)
      case Nil => Nil
    }
    println(s"CTCP message=${message.arguments.mkString("√")} arg2=${message.arguments(0)} FIRSTCHAR=${message.arguments(1).charAt(0).toInt}")
    val target = message.arguments(0)
    val commandArguments = fixTokens(message.arguments.tail.toList)
    val (command,firstArgument) = splitOnFirstSpace(commandArguments(0))
    val arguments = firstArgument::commandArguments.tail
    println(s"CTCP ${message.origin} => $target :: ${arguments.mkString("√")}")
    command match {
      case "ACTION" =>
        val action = new CTCPAction(message.origin,Target.getTargets(target,server),arguments(0))
        println(s"sending CTCPAction $action")
        onMessage(action)
      case "VERSION" if message.origin != None =>
        if (message.command == "PRIVMSG") {
          val origin = message.origin.get
          val query = new CTCPVersionQuery(origin)
          onMessage(query)
        } else {
          println(s"A CTCP version response....${message.origin} $message")
        }
      case other =>
        println(s"$this:Unhandled: $other")
    }

  }

  override def toString = "IRCServer"
}
