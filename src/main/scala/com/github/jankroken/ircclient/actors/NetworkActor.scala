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
import com.github.jankroken.ircclient.gui.{AddNetworkToTreeView, AddChannelToTreeView}

class NetworkActor(gui:ActorRef,network:String,server:String) extends Actor with ActorLogging {

  val nickAccumulator = IRCActorSystem.system.actorOf(Props(new NickAccumulatorActor),"nickAcc")
  val networkTarget = NetworkTarget(network)

  def onMessage(serverMessage:ServerMessage) = {
    self ! serverMessage
  }

  def connect() {
    val xeno = TestUser
    val freenode = new IRCServer(server)
    freenode.user = xeno
    freenode.connect(Some(onMessage(_)))
    Thread.sleep(2000)
    freenode.setNick(xeno.xenobot7)
    freenode.logon
    Thread.sleep(2000)
    freenode.join("#fealdia")
    //  freenode.join("#digitalgunfire")
    freenode.join("#xenotest")
    freenode.join("#scala")
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
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! InfoBlock(networkTarget,"Message of the day",motd.getText)
    }
    case ping:Ping ⇒ {
      gui ! InfoBlock(networkTarget,"ping",ping.toString)
    }

    case welcome:WelcomeMessage ⇒ {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! InfoBlock(networkTarget,"@Welcome Message",welcome.getText)
    }
    case topic:Topic ⇒ {
      val target = ChannelTarget("freenode",topic.channel.name)
      gui ! AddChannelToTreeView(target)
      gui ! InfoBlock(target,s"Topic",topic.topic)
    }
    case nicksForChannel:NicksForChannel ⇒ {
      // gui ! InfoBlock(s"nicks for ${nicksForChannel.channel.name}",nicksForChannel.nicks.toString)
      val target = ChannelTarget("freenode",nicksForChannel.channel.name)
      gui ! AddChannelToTreeView(target)
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
      gui ! AddChannelToTreeView(target)
      gui ! SimpleMessage(target,"",s"$joiner has joined")
    }
    case notice:Notice ⇒ {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",notice.message)
    }
    case UnknownConnectionCount(count) ⇒ {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",s"$count unknown connection(s)")
    }
    case OperatorCount(count) ⇒ {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",s"$count IRC Operators online")
    }
    case ChannelCount(count) ⇒ {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget, "", s"$count channels")
    }
    case csc:ClientServerCount => {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",csc.clientServerString)
    }
    case unidentified:Unidentified => {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",unidentified.message.toString)
    }
    case params:ServerParameters => {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"",params.arguments.mkString(" "))
    }

    case privateMessage:PrivateMessage ⇒ {
      val origin = privateMessage.origin
      val message = privateMessage.message
      privateMessage.targets.foreach { target ⇒
        target match {
          case Nick(nick) ⇒ {
            gui ! AddNetworkToTreeView(networkTarget)
            gui ! SimpleMessage(networkTarget,"xxx",s"target=$target origin=$origin message=$message")
          }
          case channel:Channel => {
            val target = ChannelTarget("freenode",channel.name)
            gui ! AddChannelToTreeView(target)
            origin match {
              case Some(nick:NickAndUserAtHost) ⇒ {
                gui ! SimpleMessage(target,nick.nick,message)
              }
              case Some(nick:NickAtHost) ⇒ {
                gui ! SimpleMessage(target,nick.nick,message)
              }
              case _ ⇒ {
                gui ! SimpleMessage(target,"xxx",s"origin=$origin message=$message")
              }
            }
          }
          case other ⇒ {
            gui ! AddNetworkToTreeView(networkTarget)
            gui ! SimpleMessage(networkTarget,"xxx",s"target=$target:${target.getClass.getSimpleName} origin=$origin message=$message")
          }
        }
      }
    }
    case serverMessage:ServerMessage ⇒ {
      gui ! AddNetworkToTreeView(networkTarget)
      gui ! SimpleMessage(networkTarget,"xxx",s"${serverMessage.getClass.getSimpleName}${serverMessage.toString})")
    }
    case foo ⇒ {
      println(s"NetworkActor: $foo")
    }
  }
}
