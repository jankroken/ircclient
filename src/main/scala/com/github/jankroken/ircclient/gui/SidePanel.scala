package com.github.jankroken.ircclient.gui

import javafx.scene.control.SplitPane
import javafx.geometry.Orientation

class SidePanel extends SplitPane {
  setId("list-splitpane")
  setMinWidth(120)
  setMaxWidth(300)
  setPrefWidth(200)
  setOrientation(Orientation.VERTICAL)
}
