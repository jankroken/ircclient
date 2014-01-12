package com.github.jankroken.ircclient.gui

import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.GridPane
import com.github.jankroken.ircclient.domain.EventListener

class ChatPane(eventListener: EventListener) extends ScrollPane {
  val chatPanel = new GridPane {

  }

  fitToWidth = true
  fitToHeight = true
  content = chatPanel
}
object ChatPane {
  def apply(eventListener: EventListener) = new ChatPane(eventListener)
}
