package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{EventListener, ChatTarget}

class ChatPanels(eventListener:EventListener) {
  var panels:Map[ChatTarget,ChatPane] = Map()
  def getPanel(target: ChatTarget) = {
    if(!panels.keySet.contains(target))
      panels = panels + (target → ChatPane(eventListener))
    panels(target)
  }

}
