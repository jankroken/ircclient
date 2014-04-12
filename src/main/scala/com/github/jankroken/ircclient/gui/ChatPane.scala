package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.EventListener
import javafx.scene.control.{Label, ScrollPane}
import javafx.scene.layout.{VBox, GridPane}

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
    val text = new VBox
    text.getStyleClass.add("infomessage")
    val titleLabel = new Label {
      setText(title)
      getStyleClass.add("infomessageHeader")
    }
    text.getChildren.add(titleLabel)
    message.split("\n").map(t ⇒ new Label { setText(t);setWrapText(true); getStyleClass.add("infomessageLine") }).foreach(text.getChildren.add)

    chatPanel.add(text,1,row)
    row = row + 1
  }

  def scrollToBottom = setVvalue(getVmax)

  def sendSimpleMessage(from:String,message:String) {
    val nick = new Label {
      setText(s"$from")
      setMinWidth(100)
      setMaxWidth(140)
      setPrefWidth(100)
    }
    val text = new VBox
    message.split("\n").map(t ⇒ new Label { setText(t); setWrapText(true) }).foreach(text.getChildren.add)
    chatPanel.add(nick,0,row)
    chatPanel.add(text,1,row)
    row = row + 1
    scrollToBottom
  }
}

object ChatPane {
  def apply(eventListener: EventListener) = new ChatPane(eventListener)
}
