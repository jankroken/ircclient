package com.github.jankroken.ircclient.actors

import akka.actor.{ActorRef, Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.protocol.domain._
import com.github.jankroken.ircclient.gui.ChatPanels
import com.github.jankroken.ircclient.domain._
import com.github.jankroken.ircclient.domain.InfoBlock
import com.github.jankroken.ircclient.protocol.domain.Ping
import com.github.jankroken.ircclient.domain.NetworkTarget
import com.github.jankroken.ircclient.protocol.domain.EndOfNames
import com.github.jankroken.ircclient.domain.NicksForChannel
import scala.Some
import com.github.jankroken.ircclient.protocol.domain.NameList

class NetworkActor(gui:ActorRef,server:String) extends Actor with ActorLogging {

//  var chatPanels:Option[ChatPanels] = None

  val nickAccumulator = IRCActorSystem.system.actorOf(Props(new NickAccumulatorActor),"nickAcc")

  def onMessage(serverMessage:ServerMessage) = {
    self ! serverMessage
  }

  def connect() {
    val xeno = TestUser
    val freenode = new IRCServer("irc.freenode.org")
    freenode.user = xeno
    freenode.connect(Some(onMessage(_)))
    Thread.sleep(2000)
    freenode.setNick(xeno.xenobot7)
    freenode.logon
    Thread.sleep(2000)
    var fealdia: Channel = freenode.join("#fealdia")
    //  var digitalgunfire: Channel = freenode.join("#digitalgunfire")
    var xenotest = freenode.join("#xenotest")
    var scala: Channel = freenode.join("#scala")
  }

  def receive = {
    case Init ⇒ {
      connect
    }
    case chatPanels:ChatPanels ⇒ {
      println(s"thread=${Thread.currentThread()}")
      gui ! chatPanels
//      this.chatPanels = Some(chatPanels)
    }
    case nameList:NameList ⇒ {
      nickAccumulator ! nameList
    }
    case endOfNames:EndOfNames ⇒ {
      nickAccumulator ! endOfNames
    }
    case motd:MessageOfTheDay ⇒ {
      gui ! InfoBlock("Message of the day",motd.getText)
    }
    case ping:Ping ⇒ {
      gui ! InfoBlock("ping",ping.toString)
    }

    case welcome:WelcomeMessage ⇒ {
      gui ! InfoBlock("@Welcome Message",welcome.getText)
    }

    case serverMessage:ServerMessage ⇒ {
      gui ! SimpleMessage("xxx",serverMessage.toString)
    }
    case nicksForChannel:NicksForChannel ⇒ {
      gui ! InfoBlock(s"nicks for ${nicksForChannel.channel.name}",nicksForChannel.nicks.toString)
    }
    case foo ⇒ {
      println(s"NetworkActor: $foo")
    }
  }
}
