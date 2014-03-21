package com.github.jankroken.ircclient.actors

import akka.actor.{ActorRef, Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.protocol.domain._
import com.github.jankroken.ircclient.domain._
import com.github.jankroken.ircclient.domain.InfoBlock
import com.github.jankroken.ircclient.protocol.domain.Ping
import com.github.jankroken.ircclient.protocol.domain.Nick
import com.github.jankroken.ircclient.domain.ChannelTarget
import com.github.jankroken.ircclient.protocol.domain.NickAndUserAtHost
import com.github.jankroken.ircclient.protocol.domain.Join
import com.github.jankroken.ircclient.protocol.domain.EndOfNames
import com.github.jankroken.ircclient.protocol.domain.PrivateMessage
import scala.Some
import com.github.jankroken.ircclient.protocol.domain.NameList
import com.github.jankroken.ircclient.protocol.domain.Topic
import com.github.jankroken.ircclient.domain.SimpleMessage
import com.github.jankroken.ircclient.domain.NetworkTarget
import com.github.jankroken.ircclient.domain.NickList
import com.github.jankroken.ircclient.domain.NicksForChannel

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
    case join:Join ⇒ {
      val target = ChannelTarget("freenode",join.channel.name)
      val joiner = join.origin match {
        case None => "no one"
        case Some(nickUser:NickAndUserAtHost) => nickUser.nick
        case Some(x) => s"unknown=$x"
      }
      gui ! SimpleMessage(target,"",s"$joiner has joined")
    }
    case privateMessage:PrivateMessage ⇒ {
      val origin = privateMessage.origin
      val message = privateMessage.message
      privateMessage.targets.foreach { target ⇒
        target match {
          case Nick(nick) ⇒ {
            gui ! SimpleMessage(NetworkTarget("freenode"),"xxx",s"target=$target origin=$origin message=$message")

          }
          case channel:Channel => {
            val target = ChannelTarget("freenode",channel.name)
            origin match {
              case Some(nick:NickAndUserAtHost) => {
                gui ! SimpleMessage(target,nick.nick,message)
              }
              case Some(nick:NickAtHost) => {
                gui ! SimpleMessage(target,nick.nick,message)
              }
              case _ => {
                gui ! SimpleMessage(target,"xxx",s"origin=$origin message=$message")
              }
            }
          }
          case other ⇒ {
            gui ! SimpleMessage(NetworkTarget("freenode"),"xxx",s"target=$target:${target.getClass.getSimpleName} origin=$origin message=$message")
          }
        }
      }
    }
    case serverMessage:ServerMessage ⇒ {
      gui ! SimpleMessage(NetworkTarget("freenode"),"xxx",s"${serverMessage.getClass.getSimpleName}${serverMessage.toString})")
    }
    case foo ⇒ {
      println(s"NetworkActor: $foo")
    }
  }
}
