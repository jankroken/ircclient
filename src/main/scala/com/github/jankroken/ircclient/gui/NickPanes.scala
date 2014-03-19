package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{ChatTarget, EventListener}

class NickPanes(eventListener:EventListener) {
  var panels:Map[ChatTarget,NickPane] = Map()
  def getPanel(target: ChatTarget) = {
  if(!panels.keySet.contains(target))
    panels = panels + (target → NickPane(eventListener))
    panels(target)
  }
}
