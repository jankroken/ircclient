package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain._
import javafx.scene.control.TextField
import javafx.event.EventHandler
import javafx.scene.input.{KeyCode, KeyEvent}
import com.github.jankroken.ircclient.domain.LineEntered
import scala.Some
import com.github.jankroken.ircclient.domain.InputFieldValue

class CommandLine(eventListener:EventListener) extends TextField {
  setPromptText("Command line")
  val history = new CommandHistory
  setOnKeyTyped(new EventHandler[KeyEvent] {
    def handle(e:KeyEvent) {
        val c = e.getCharacter
        val updatedText = InputModifier.replaceSymbols(getText)
        if (getText != updatedText) {
          val pos = getCaretPosition
          setText(updatedText)
          positionCaret(pos)
        }
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
        case KeyCode.TAB ⇒
          val text = getText
          val position = getCaretPosition
          val field = InputFieldValue(text,position)
          println(s"tab pressed nick:${field.potentialNickStart}")
          field.findLongestSafeNickMatch(ActiveNicks.activeNicks)
          println(s"# ${}")
          e.consume
        case other ⇒ // println(s"key pressed: $other") // others are ignored
      }
    }
  })
}
