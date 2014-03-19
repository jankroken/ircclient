package com.github.jankroken.ircclient.actors

import akka.actor.{ActorRef, Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.protocol.domain._
import com.github.jankroken.ircclient.domain._
import com.github.jankroken.ircclient.domain.InfoBlock
import com.github.jankroken.ircclient.protocol.domain.Ping
import com.github.jankroken.ircclient.protocol.domain.EndOfNames
import com.github.jankroken.ircclient.domain.NicksForChannel
import scala.Some
import com.github.jankroken.ircclient.protocol.domain.NameList

class NetworkActor(gui:ActorRef,server:String) extends Actor with ActorLogging {

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
    case nameList:NameList ⇒ {
      nickAccumulator ! nameList
    }
    case endOfNames:EndOfNames ⇒ {
      nickAccumulator ! endOfNames
    }
    case motd:MessageOfTheDay ⇒ {
      gui ! InfoBlock(NetworkTarget("freenode"),"Message of the day",motd.getText)
    }
    case ping:Ping ⇒ {
      gui ! InfoBlock(NetworkTarget("freenode"),"ping",ping.toString)
    }

    case welcome:WelcomeMessage ⇒ {
      gui ! InfoBlock(NetworkTarget("freenode"),"@Welcome Message",welcome.getText)
    }
    case topic:Topic ⇒ {
      val target = ChannelTarget("freenode",topic.channel.name)
      gui ! InfoBlock(target,s"Topic",topic.topic)
    }
    case nicksForChannel:NicksForChannel ⇒ {
      // gui ! InfoBlock(s"nicks for ${nicksForChannel.channel.name}",nicksForChannel.nicks.toString)
      val target = ChannelTarget("freenode",nicksForChannel.channel.name)
      gui ! NickList(target,nicksForChannel.nicks)
      //      gui ! NickList(nicksForChannel.channel.)
    }
    case serverMessage:ServerMessage ⇒ {
      gui ! SimpleMessage("xxx",serverMessage.toString)
    }
    case foo ⇒ {
      println(s"NetworkActor: $foo")
    }
  }
}
