package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{CommandHistory, LineEntered, EventListener}
import javafx.scene.control.TextField
import javafx.event.EventHandler
import javafx.scene.input.{KeyCode, KeyEvent}

class CommandLine(eventListener:EventListener) extends TextField {
  setPromptText("Command line")
  val history = new CommandHistory
  setOnKeyTyped(new EventHandler[KeyEvent] {
    def handle(e:KeyEvent) {
        val c = e.getCharacter
        val updatedText = InputModifier.replaceSymbols(getText)
        if (getText != updatedText) {
          val pos = getCaretPosition
//          println(s"position: ${pos}")
          setText(updatedText)
          positionCaret(pos)
        }
//        println(s"keyEvent:  $c $e ${getText}")
    }
  })

  setOnKeyPressed(new EventHandler[KeyEvent] {
    def handle(e:KeyEvent) {
      e.getCode match {
        case KeyCode.ENTER ⇒
          val text = getText
          eventListener.onEvent(LineEntered(text))
          history.add(text)
          setText("")
        case KeyCode.UP ⇒
          history.jumpBack match {
            case Some(text) ⇒ setText(text)
            case None ⇒
          }
        case KeyCode.DOWN ⇒
          history.jumpForward match {
            case Some(text) ⇒ setText(text)
            case None ⇒
          }
        case _ ⇒ // others are ignored
      }
    }
  })
}
