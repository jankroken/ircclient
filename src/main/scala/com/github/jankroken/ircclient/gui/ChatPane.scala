package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.EventListener
import javafx.scene.control.ScrollPane
import javafx.scene.layout.GridPane

class ChatPane(eventListener: EventListener) extends ScrollPane {
  val chatPanel = new GridPane {
    setFitToWidth(true)
    setFitToHeight(true)
    setManaged(true)
    setMaxWidth(10000)
    setMaxHeight(10000)
  }

  setFitToWidth(true)
  setFitToHeight(true)
  setContent(chatPanel)
  setManaged(true)
  setMaxHeight(10000)
  setMaxWidth(10000)
  setMinWidth(100)
}

object ChatPane {
  def apply(eventListener: EventListener) = new ChatPane(eventListener)
}
