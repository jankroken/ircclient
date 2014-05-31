package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.EventListener
import javafx.scene.control.{Label, ScrollPane}
import javafx.scene.layout.GridPane

class NickPane(eventListener:EventListener) extends ScrollPane {
  val nickTable = new GridPane

  setMinWidth(100)
  setMinHeight(100)
  setPrefHeight(100)
  setFitToWidth(true)
  setFitToHeight(true)
  setId("page-tree")
  setContent(nickTable)

  def setNicks(nicks:List[String]) {
    val nickLabels = nicks.map(nick ⇒ new Label { setText(nick) })
    nickTable.getChildren.removeAll()
    (0 to nicks.length-1).foreach { n ⇒
      nickTable.addRow(n,nickLabels(n))
    }
  }

}

object NickPane {
  def apply(eventListener:EventListener) = new NickPane(eventListener)
}
