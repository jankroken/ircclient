package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{EventListener, ChatTarget}

class ChatPanels(eventListener:EventListener) {
  var panels:Map[ChatTarget,HTMLChatPane] = Map()
  def getPanel(target: ChatTarget) = {
    if(!panels.keySet.contains(target))
      panels = panels + (target → HTMLChatPane(eventListener))
    panels(target)
  }

}
