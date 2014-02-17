package com.github.jankroken.ircclient.javafx

import com.github.jankroken.ircclient.domain.{LineEntered, EventListener}
import javafx.scene.control.TextField
import javafx.event.EventHandler
import javafx.scene.input.{KeyCode, KeyEvent}

class CommandLine(eventListener:EventListener) extends TextField {
  setPromptText("Command line")
  setOnKeyTyped(new EventHandler[KeyEvent] {
    def handle(e:KeyEvent) {
        val c = e.getCharacter
        val updatedText = InputModifier.replaceSymbols(getText)
        if (getText != updatedText) {
          val pos = getCaretPosition
          println(s"position: ${pos}")
          setText(updatedText)
          positionCaret(pos)
        }
        println(s"keyEvent:  $c $e ${getText}")
    }
  })

  setOnKeyPressed(new EventHandler[KeyEvent] {
    def handle(e:KeyEvent) {
      e.getCode match {
        case KeyCode.ENTER ⇒ {
          eventListener.onEvent(LineEntered(getText))
          setText("")
        }
        case KeyCode.UP ⇒ {
          setText("UP PRESSED")
        }
        case _ ⇒ // others are ignored
      }
    }
  })
}
