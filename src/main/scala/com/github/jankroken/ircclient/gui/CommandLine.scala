package com.github.jankroken.ircclient.gui

import scalafx.scene.control.TextField
import scalafx.Includes._
import scalafx.scene.input.{KeyCode, KeyEvent}
import com.github.jankroken.ircclient.domain.{LineEntered, EventListener}

class CommandLine(eventListener:EventListener) extends TextField {
  promptText = "Command line"
  onKeyTyped = (e:KeyEvent) => {
      if (e.character == '\n') println("NEWLINE")
    println(s"keyEvent:  ${e.character} $e ${text.value}")
  }
  onKeyPressed = (e:KeyEvent) => {
    e.code match {
      case KeyCode.ENTER => {
        eventListener.onEvent(LineEntered(text.value))
        text.value = ""
      }
      case KeyCode.UP => {
        text.value = "UP PRESSED"
      }
      case _ => // others are ignored
    }
  }
}
