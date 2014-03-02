package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.EventListener
import javafx.scene.control.{Label, ScrollPane}
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

  def sendSimpleMessage(from:String,message:String) {
    val nick = new Label {
      setText(s"$from")
      setMinWidth(100)
      setMaxWidth(140)
      setPrefWidth(100)
    }
    val text = new Label { setText(s"$message")}
  }
}

object ChatPane {
  def apply(eventListener: EventListener) = new ChatPane(eventListener)
}
