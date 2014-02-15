package com.github.jankroken.ircclient.javafx

import com.github.jankroken.ircclient.domain.{LineEntered, EventListener}
import com.github.jankroken.ircclient.gui.InputModifier
import javafx.scene.control.TextField
import javafx.event.EventHandler
import javafx.scene.input.KeyEvent

class CommandLine(eventListener:EventListener) extends TextField {
  setPromptText("Command line")
  setOnKeyTyped(new EventHandler[KeyEvent] {
    def handle(e:KeyEvent) {
      println("hello world")
    }
  })

//  onKeyTyped = (e:KeyEvent) ⇒ {
//    val c = e.character
//    val updatedText = InputModifier.replaceSymbols(text.value)
//    if (text.value != updatedText) {
//      val pos = caretPosition
//      println(s"position: ${pos.value}")
//      text.value = updatedText
//      delegate.positionCaret(pos.value)
//    }
//    println(s"keyEvent:  $c $e ${text.value}")
//  }
//  onKeyPressed = (e:KeyEvent) ⇒ {
//    e.code match {
//      case KeyCode.ENTER ⇒ {
//        eventListener.onEvent(LineEntered(text.value))
//        text.value = ""
//      }
//      case KeyCode.UP ⇒ {
//        text.value = "UP PRESSED"
//      }
//      case _ ⇒ // others are ignored
//    }
//  }
}
