package com.github.jankroken.ircclient.actors

import akka.actor.{ActorRef, Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.protocol.domain._
import com.github.jankroken.ircclient.domain._
import com.github.jankroken.ircclient.gui.{AddNetworkToTreeView, AddChannelToTreeView}
import com.github.jankroken.ircclient.commands.{TextCommand, JoinCommand, IdentifiedCommand}
import java.net.UnknownHostException

class NetworkActor(gui:ActorRef,network:String,server:String) extends Actor with ActorLogging {

  val nickAccumulator = IRCActorSystem.system.actorOf(Props(new NickAccumulatorActor),s"$server:nickAcc")
  val networkTarget = NetworkTarget(network)
  var ircServer = new IRCServer(server)
  var xenotest:Channel = null
  var fealdia:Channel = null
  var politics:Channel = null
  val xeno = TestUser

  def onMessage(serverMessage:ServerMessage) = {
    self ! serverMessage
  }

  def connect() {
    ircServer.user = xeno
    ircServer.connect(Some(onMessage(_)))
    Thread.sleep(2000)
    ircServer.setNick(xeno.xenobot7)
    ircServer.logon
    Thread.sleep(2000)
    xenotest = ircServer.join("#xenotest")
    val scala = ircServer.join("#scala")

  }

  def receive = {
    case Init ⇒
      try {
        gui ! InfoBlock(networkTarget,"Client","Connecting...")
        println(s"Connecting: $network $server")
        connect()
        gui ! InfoBlock(networkTarget,"Client","Connected")
      } catch {
        case e:UnknownHostException => gui ! InfoBlock(networkTarget,"Failed to connect", e.getMessage)
        case e => gui ! InfoBlock(networkTarget,"Failed to connect",e.toString)
        sender ! Disconnected
      }
    case nameList: NameList ⇒
      nickAccumulator ! nameList
    case endOfNames: EndOfNames ⇒
      nickAccumulator ! endOfNames
    case motd: MessageOfTheDay ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! InfoBlock(networkTarget, "Message of the day", motd.getText)
    case ping: Ping ⇒
      ircServer.pong(ping.servername)
      gui ! InfoBlock(networkTarget, "ping", ping.toString)
    case welcome: WelcomeMessage ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! InfoBlock(networkTarget, "@Welcome Message", welcome.getText)
    case topic: Topic ⇒
      val target = ChannelTarget(network, topic.channel.name)
      gui ! AddChannelToTreeView(target)
      gui ! InfoBlock(target, s"Topic", topic.topic)
    case nicksForChannel: NicksForChannel ⇒
      // gui ! InfoBlock(s"nicks for ${nicksForChannel.channel.name}",nicksForChannel.nicks.toString)
      val target = ChannelTarget(network, nicksForChannel.channel.name)
      gui ! AddChannelToTreeView(target)
      gui ! NickList(target, nicksForChannel.nicks)
    //      gui ! NickList(nicksForChannel.channel.)
    case join: Join ⇒
      val target = ChannelTarget(network, join.channel.name)
      val joiner: String = join.origin match {
        case None ⇒ "no one"
        case Some(nickUser: NickAndUserAtHost) ⇒ nickUser.nick
        case Some(x) ⇒ s"unknown=$x"
      }
      gui ! AddChannelToTreeView(target)
      gui ! SimpleMessage(target, "", s"$joiner has joined")
    case notice:Notice ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",notice.message)
    case UnknownConnectionCount(count) ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",s"$count unknown connection(s)")
    case OperatorCount(count) ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",s"$count IRC Operators online")
    case ChannelCount(count) ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget, "", s"$count channels")
    case csc:ClientServerCount ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",csc.clientServerString)
    case unidentified:Unidentified ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",unidentified.message.toString)
    case params:ServerParameters ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",params.arguments.mkString(" "))

    case privateMessage:PrivateMessage ⇒
      val origin = privateMessage.origin
      val message = privateMessage.message
      privateMessage.targets.foreach { target ⇒
        target match {
          case Nick(nick) ⇒
            gui ! AddNetworkToTreeView(networkTarget)
            gui ! SimpleMessage(networkTarget,"",s"target=$target origin=$origin message=$message")
          case channel:Channel ⇒
            val target = ChannelTarget(network,channel.name)
            gui ! AddChannelToTreeView(target)
            origin match {
              case Some(nick:NickAndUserAtHost) ⇒
                gui ! SimpleMessage(target,nick.nick,message)
              case Some(nick:NickAtHost) ⇒
                gui ! SimpleMessage(target,nick.nick,message)
              case _ ⇒
                gui ! SimpleMessage(target,"",s"origin=$origin message=$message")
            }
          case other ⇒
            gui ! AddNetworkToTreeView(networkTarget)
            gui ! SimpleMessage(networkTarget,"",s"target=$target:${target.getClass.getSimpleName} origin=$origin message=$message")
        }
      }
    case serverMessage:ServerMessage ⇒
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",s"${serverMessage.getClass.getSimpleName}${serverMessage.toString})")
//    case text:IdentifiedCommand.Text ⇒
//      gui ! AddChannelToTreeView(ChannelTarget(network,fealdia.name))
//      ircServer.message(fealdia,text.param)
//      gui ! SimpleMessage(ChannelTarget(network,fealdia.name),xeno.nick,text.param)
    case TextCommand(channelTarget,message) ⇒
      gui ! AddChannelToTreeView(channelTarget)
      ircServer.message(Channel(channelTarget.channel,ircServer),message)
      gui ! SimpleMessage(channelTarget,xeno.nick,message)
    case JoinCommand(channelTarget) ⇒
      gui ! AddChannelToTreeView(channelTarget)
      ircServer.join(channelTarget.channel)
    case other ⇒
      println(s"NetworkActor: $other")
  }
}
