package com.github.jankroken.ircclient.actors

import akka.actor.{Actor, ActorLogging}
import com.github.jankroken.ircclient.gui._
import com.github.jankroken.ircclient.domain.InfoBlock
import com.github.jankroken.ircclient.domain.SimpleMessage
import com.github.jankroken.ircclient.domain.NickList
import com.github.jankroken.ircclient.gui.AddChannelToTreeView
import scala.Some

class GUIActor extends Actor with ActorLogging {

  var chatPanels:Option[ChatPanels] = None
  var nickPanels:Option[NickPanes] = None
  var channelPane:Option[ChannelPane] = None

  def receive = {
    case chatPanels:ChatPanels ⇒
      this.chatPanels = Some(chatPanels)
    case nickPanes:NickPanes ⇒
      this.nickPanels = Some(nickPanes)
    case channelPane:ChannelPane ⇒
      this.channelPane = Some(channelPane)
    case info:InfoBlock ⇒
      chatPanels match {
        case None ⇒
          println(s"onMessage: $info")
        case Some(cp) ⇒
          val panel = cp.getPanel(info.target)
          panel.sendTextInfoBlock(info.header,info.content)
      }

    case message:SimpleMessage ⇒
      chatPanels match {
        case None ⇒
          println(s"onMessage: $message")
        case Some(cp) ⇒
          val panel = cp.getPanel(message.target)
          panel.sendSimpleMessage(message.from,message.message)
      }

    case nickList:NickList ⇒
      nickPanels match {
        case None ⇒
          println(s"onMessage: $nickList")
        case Some(cp) ⇒
          val panel = cp.getPanel(nickList.chatTarget)
          panel.setNicks(nickList.nicks)
      }

    case AddChannelToTreeView(target) ⇒
      channelPane match {
        case None ⇒ println(s"addChannelToTreeView: $target")
        case Some(cp) ⇒ cp.addOrModifyChannel(target)
      }
    case AddNetworkToTreeView(target) ⇒
      channelPane match {
        case None ⇒ println(s"addChannelToTreeView: $target")
        case Some(cp) ⇒ cp.addOrModifyNetwork(target)
      }

    case unknown ⇒
      println(s"GUIActor: $unknown")
  }
}
