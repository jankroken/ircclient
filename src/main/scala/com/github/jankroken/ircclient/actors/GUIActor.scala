package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.{NickPanes, ChatPanels}
import com.github.jankroken.ircclient.domain._
import com.github.jankroken.ircclient.domain.InfoBlock
import scala.Some
import com.github.jankroken.ircclient.domain.NetworkTarget

class GUIActor(server:String) extends Actor with ActorLogging {

  var chatPanels:Option[ChatPanels] = None
  var nickPanels:Option[NickPanes] = None

  def receive = {
    case chatPanels:ChatPanels ⇒ {
      println(s"thread=${Thread.currentThread()}")
      this.chatPanels = Some(chatPanels)
    }
    case nickPanes:NickPanes ⇒ {
      println(s"thread=${Thread.currentThread()}")
      this.nickPanels = Some(nickPanes)
    }
    case info:InfoBlock ⇒ {
      println(s"thread=${Thread.currentThread()}")
      chatPanels match {
        case None ⇒ {
          println(s"onMessage: $info")
        }
        case Some(cp) ⇒ {
          val panel = cp.getPanel(info.target)
          panel.sendTextInfoBlock(info.header,info.content)
        }
      }
    }

    case message:SimpleMessage ⇒ {
      chatPanels match {
        case None ⇒ {
          println(s"onMessage: $message")
        }
        case Some(cp) ⇒ {
          println("sending to chatpanels")
          val panel = cp.getPanel(NetworkTarget("freenode"))
          panel.sendSimpleMessage(message.from,message.message)
        }
      }
    }

    case nickList:NickList ⇒ {
      nickPanels match {
        case None ⇒ {
          println(s"onMessage: $nickList")
        }
        case Some(cp) ⇒ {
          println("sending to chatpanels")
          val panel = cp.getPanel(nickList.chatTarget)
          panel.setNicks(nickList.nicks)
        }
      }
    }
    case foo ⇒ {
      println(s"GUIActor: $foo")
    }
  }
}
