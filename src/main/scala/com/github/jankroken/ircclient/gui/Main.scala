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
import com.github.jankroken.ircclient.domain.ChannelSelected

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

  val nickPanel = new ScrollPane {
    minWidth    = 100
    minHeight   = 100
    prefHeight  = 200
    fitToWidth  = true
    fitToHeight = true
    id = "page-tree"
    content = new VBox {
      content = List(
        new Label { text = "@Socrates" },
        new Label { text = "Plato" },
        new Label { text = "Xenophon"},
        new Label { text = "Crito"})
    }
  }

  val channelPanel = new ScrollPane {
      fitToWidth = true
      fitToHeight = true
      minHeight = 200
      prefHeight = 2000

    id = "page-tree"

    def networkChannels(network:String,channels:List[String]) =
      new TreeView[String]{
        root = new TreeItem[String](network) {
          expanded = true
          children = channels.map(new TreeItem(_))
        }
        selectionModel().selectedItem.onChange { (_,_,newVal) =>
          val channelSelected = ChannelSelected(network,newVal.value.value)
          println(s"$channelSelected")
        }
      }

    content = new VBox {
//      fitToHeight = false
      content = List(networkChannels("freenode",List("#scala","#java","#haskell")),
                     networkChannels("efnet",List("#ocaml","#prolog","#ada")),
                     networkChannels("quakenet",List("#quake","#doom#","#wolfenstein","#keen","#doom2","#doom3","quake2")))
    }
  }

  val sidePanel = new SplitPane {
    minWidth = 120
    maxWidth = 300
    prefWidth = 150
    dividerPositions = 1
    orientation = Orientation.VERTICAL

    id = "list-splitpane"
    items.addAll(nickPanel,channelPanel)
  }

  val ob = ObservableBuffer[HBox]()

  val chatPanel = new GridPane{

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
    chatPanel.addRow(n,lines(n))
  }}

}
