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

  val initialNicks = List("@Socrates","Plato","Xenophon","Crito").map(nick ⇒ new Label { setText(nick) })
  (0 to initialNicks.length-1).foreach { n ⇒
    println(s"Adding nick: ${initialNicks(n)}")
    nickTable.addRow(n,initialNicks(n))
  }

}

object NickPane {
  def apply(eventListener:EventListener) = new NickPane(eventListener)
}
