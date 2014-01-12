package com.github.jankroken.ircclient.gui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Orientation
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.collections.ObservableBuffer
import scalafx.Includes._
import com.github.jankroken.ircclient.domain.{EventListener}
import scalafx.scene.image.{Image, ImageView}

object Main extends JFXApp {

  val lines = List.range(1,1000).map(num => new HBox {
    content = List(new Label {
      text = s"Hello$num"
      minWidth = 100
      maxWidth = 140
      prefWidth = 100
      textFill = Color.BLUE
    }, new Label { text= s"Hello $num"})
  })

  val sidePanel = new SplitPane {
    minWidth = 120
    maxWidth = 300
    prefWidth = 150
    dividerPositions = 1
    orientation = Orientation.VERTICAL

    id = "list-splitpane"
    val eventListener = new EventListener
    items.addAll(NickPane(eventListener),ChannelPane(eventListener))
  }

  val ob = ObservableBuffer[HBox]()

  val chatPanel = new GridPane{
//    val row1 = new RowConstraints()
//    row1.setVgrow(Priority.ALWAYS)
//    rowConstraints = List(row1)
  }

  val channel = new ScrollPane {
    fitToWidth = true
    fitToHeight = true
    content = chatPanel
  }

  stage = new PrimaryStage {
    title = "IRC Client"
    scene = new Scene(1020, 700) {
      root = new BorderPane {
        center =  new SplitPane {
          dividerPositions = 0
          id = "page-splitpane"
          items.addAll(sidePanel,channel)
        }
        bottom = new TextField {
          promptText = "command line"
        }
      }
    }
  }

  List.range(0,999).foreach{n => {

    if (n != 997)
      chatPanel.addRow(n,lines(n))
    else {
      val url = this.getClass.getClassLoader.getResource("mobius_building.jpeg").toExternalForm
      val sample1 = new ImageView(new Image(url, requestedWidth = 300, requestedHeight = 120, preserveRatio = true, smooth = true))
      chatPanel.addRow(n,sample1)
    }
  }}

}
