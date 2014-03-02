package com.github.jankroken.ircclient.gui

import javafx.scene.control.SplitPane
import javafx.geometry.Orientation

/**
 * Created by jan on 02/03/14.
 */
class SidePanel extends SplitPane {
  setId("list-splitpane")
//    setDividerPosition(1,0)
  setMinWidth(120)
  setMaxWidth(300)
  setPrefWidth(200)
  setOrientation(Orientation.VERTICAL)
}
