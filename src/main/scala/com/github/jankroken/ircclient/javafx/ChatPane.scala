package com.github.jankroken.ircclient.javafx

import com.github.jankroken.ircclient.domain.EventListener
import javafx.scene.control.ScrollPane
import javafx.scene.layout.GridPane

class ChatPane(eventListener: EventListener) extends ScrollPane {
  val chatPanel = new GridPane {
    setFitToWidth(true)
    setFitToHeight(true)
  }

  setFitToWidth(true)
  setFitToHeight(true)
  setContent(chatPanel)
}

object ChatPane {
  def apply(eventListener: EventListener) = new ChatPane(eventListener)
}
