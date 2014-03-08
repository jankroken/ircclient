package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.EventListener
import javafx.scene.control.{Button, Label, ScrollPane}
import javafx.scene.layout.GridPane

class ChatPane(eventListener: EventListener) extends ScrollPane {
  var row = 1

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

  def sendTextInfoBlock(title:String,message:String) {
    println(s"Making button of: $message")
    val button = new Button(message)
    chatPanel.add(button,1,row)
    row = row + 1
  }

  def sendSimpleMessage(from:String,message:String) {
    val nick = new Label {
      setText(s"$from")
      setMinWidth(100)
      setMaxWidth(140)
      setPrefWidth(100)
    }
    val text = new Label { setText(s"$message")}
    chatPanel.add(nick,0,row)
    chatPanel.add(text,1,row)
    row = row + 1
  }
}

object ChatPane {
  def apply(eventListener: EventListener) = new ChatPane(eventListener)
}
