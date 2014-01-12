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
import com.github.jankroken.ircclient.domain.{NetworkSelected, ChannelSelected}
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

  val nickPanel = new ScrollPane {
    minWidth    = 100
    minHeight   = 100
    prefHeight  = 100
    fitToWidth  = true
    fitToHeight = true
    id = "page-tree"
    content = new VBox {
      content = List("@Socrates","Plato","Xenophon","Crito").map(nick => new Label { text = nick })
    }
  }

  val channelTrees = new GridPane {
    //      fitToHeight = true
  }

  def ncChildren(network:String,channels:List[String]) =
      new TreeItem[String](network) {
        expanded = true
        children = channels.map(new TreeItem(_))
      }


  def networksChannels =
    new TreeView[String]{
      root = new TreeItem[String]("networks") {
        visible = true
        vgrow = Priority.ALWAYS
        expanded = true
        showRoot = false
        children = List(ncChildren("freenode",List("#scala","#java","#haskell")),
                        ncChildren("efnet",List("#ocaml","#prolog","#ada")),
                        ncChildren("quakenet",List("#quake","#doom#","#wolfenstein","#keen","#doom2","#doom3","quake2")))
      }
      selectionModel().selectedItem.onChange { (_,_,newVal) =>
        val parentValue = newVal.getParent.value.value
        val value = newVal.value.value
        val isChannel = newVal.isLeaf
        val selected = if(isChannel) ChannelSelected(parentValue,value) else NetworkSelected(value)
        println(s"$selected")
      }
      vgrow = Priority.ALWAYS
    }


  val channelPanel = new ScrollPane {
      fitToWidth = true
      fitToHeight = true
      minHeight = 200
      prefHeight = 2000

    id = "page-tree"

    content = networksChannels
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
