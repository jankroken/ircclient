package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.EventListener
import scalafx.scene.control.{Label, ScrollPane}
import scalafx.scene.layout.{GridPane, VBox}

class NickPane(eventListener:EventListener) extends ScrollPane {
  val nickTable = new GridPane {

  }

  minWidth    = 100
  minHeight   = 100
  prefHeight  = 100
  fitToWidth  = true
  fitToHeight = true
  id = "page-tree"
  content = nickTable

  val initialNicks = List("@Socrates","Plato","Xenophon","Crito").map(nick => new Label { text = nick })
  (0 to initialNicks.length-1).foreach { n =>
    nickTable.addRow(n,initialNicks(n))
  }



}
object NickPane {
  def apply(eventListener:EventListener) = new NickPane(eventListener)
}
