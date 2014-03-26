package com.github.jankroken.ircclient.actors

import akka.actor.{Props, Actor, ActorLogging}
import com.github.jankroken.ircclient.gui.{ChannelPane, AddChannelToTreeView, NickPanes, ChatPanels}
import com.github.jankroken.ircclient.domain._
import com.github.jankroken.ircclient.domain.InfoBlock
import scala.Some
import com.github.jankroken.ircclient.domain.NetworkTarget

class GUIActor(server:String) extends Actor with ActorLogging {

  var chatPanels:Option[ChatPanels] = None
  var nickPanels:Option[NickPanes] = None
  var channelPane:Option[ChannelPane] = None

  def receive = {
    case chatPanels:ChatPanels ⇒ {
      this.chatPanels = Some(chatPanels)
    }
    case nickPanes:NickPanes ⇒ {
      this.nickPanels = Some(nickPanes)
    }
    case channelPane:ChannelPane ⇒ {
      this.channelPane = Some(channelPane)
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
          val panel = cp.getPanel(message.target)
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

    case AddChannelToTreeView(target) ⇒ {
      channelPane match {
        case None  ⇒ {
          println(s"addChannelToTreeView: $target")
        }
        case Some(cp) ⇒ {
          println("sending to channelPane")
          cp.addOrModifyChannel(target)
          println("/")
        }
      }
    }
    case foo ⇒ {
      println(s"GUIActor: $foo")
    }
  }
}
