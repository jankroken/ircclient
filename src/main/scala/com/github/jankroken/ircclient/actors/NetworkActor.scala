package com.github.jankroken.ircclient.actors

import akka.actor.{Actor, ActorLogging}
import com.github.jankroken.ircclient.protocol.domain._
import com.github.jankroken.ircclient.gui.ChatPanels
import com.github.jankroken.ircclient.domain.NetworkTarget

class NetworkActor(server:String) extends Actor with ActorLogging {

  var chatPanels:Option[ChatPanels] = None

  def onMessage(serverMessage:ServerMessage) = {
    self ! serverMessage
  }

  val xeno = TestUser
  val freenode = new IRCServer("irc.freenode.org")
  freenode.user = xeno
  freenode.connect(Some(onMessage(_)))
  Thread.sleep(2000)
  freenode.setNick(xeno.xenobot7)
  freenode.logon
  Thread.sleep(4000)
  var fealdia: Channel = freenode.join("#fealdia")
  var digitalgunfire: Channel = freenode.join("#digitalgunfire")
  var scala: Channel = freenode.join("#scala")

  def receive = {
    case chatPanels:ChatPanels ⇒ {
      println(s"thread=${Thread.currentThread()}")
      this.chatPanels = Some(chatPanels)
    }
    case motd:MessageOfTheDay ⇒ {
      println(s"thread=${Thread.currentThread()}")
      chatPanels match {
        case None ⇒ {
          println(s"onMessage: $motd")
        }
        case Some(cp) ⇒ {
          val panel = cp.getPanel(NetworkTarget("freenode"))
          panel.sendTextInfoBlock("Message of the day",motd.getText)
        }
      }
    }

    case welcome:WelcomeMessage ⇒ {
      println(s"thread=${Thread.currentThread()}")
      chatPanels match {
        case None ⇒ {
          println(s"onMessage: $welcome")
        }
        case Some(cp) ⇒ {
          val panel = cp.getPanel(NetworkTarget("freenode"))
          panel.sendTextInfoBlock("Welcome Message",welcome.getText)
        }
      }
    }

    case serverMessage:ServerMessage ⇒  {
      chatPanels match {
        case None ⇒ {
          println(s"onMessage: $serverMessage")
        }
        case Some(cp) ⇒ {
          println("sending to chatpanels")
          val panel = cp.getPanel(NetworkTarget("freenode"))
          panel.sendSimpleMessage("xxx",serverMessage.toString)
        }
      }
    }

    case foo ⇒ {
      println(s"NetworkActor: $foo")
    }
  }
}