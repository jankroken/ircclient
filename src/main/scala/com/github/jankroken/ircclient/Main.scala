package com.github.jankroken.ircclient

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.Priority
import scalafx.scene.layout.Region
import scalafx.scene.layout.VBox
import scalafx.scene.Scene
import scalafx.stage.Screen


object Main extends JFXApp {

  val channelTreeView= new TreeItem[String]("Channels") {
    expanded = true
//    children = EnsembleTree.create().getTree
  }

  val nickPanel = new ScrollPane {
    minWidth = 200
    maxWidth = 200
    minHeight = 300
    fitToWidth = true
    fitToHeight = true
    id = "page-tree"
    //    content = channelTreeView
  }

  val channelPanel = new ScrollPane {
    minWidth = 200
    maxWidth = 200
    minHeight = 400

    fitToWidth = true
    fitToHeight = true
    id = "page-tree"
//    content = channelTreeView
  }

  val sidePanel = new VBox {
    minWidth = 200
    maxWidth = 200
    minHeight = 700
//    fitToHeight = true
    content = List(nickPanel,channelPanel)
  }


  stage = new PrimaryStage {
    title = "IRC Client"
    scene = new Scene(1020, 700) {
      root = new BorderPane {
        top = new VBox {

        }
        center =  new SplitPane {
          dividerPositions = 0
          id = "page-splitpane"
//          items.addAll(channelPanel)
          items.addAll(sidePanel)
        }
        bottom = new TextField {
          promptText = "command line"
//          maxWidth = 200
        }
      }
    }
  }
}


/*

package scalafx.ensemble

import scala.Some
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.ensemble.commons.PageDisplayer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.scene.layout._
import scalafx.stage.Screen

/** The main ScalaFX Ensemble application object. */
object Ensemble extends JFXApp {

  //
  // Example selection tree
  //
  var centerPane = PageDisplayer.choosePage("dashBoard")
  val rootTreeItem = new TreeItem[String]("ScalaFX Ensemble") {
    expanded = true
    children = EnsembleTree.create().getTree
  }

  val screen = Screen.primary
  val controlsView = new TreeView[String]() {
    minWidth = 200
    maxWidth = 200
    editable = true
    root = rootTreeItem
    id = "page-tree"
  }
  controlsView.selectionModel().selectionMode = SelectionMode.SINGLE
  controlsView.selectionModel().selectedItem.onChange {
    (_, _, newItem) => {
      val pageCode = (newItem.isLeaf, Option(newItem.getParent)) match {
        case (true, Some(parent)) => parent.getValue.toLowerCase + " > " + newItem.getValue
        case (false, Some(_))     => "dashBoard - " + newItem.getValue
        case (_, _)               => "dashBoard"
      }
      centerPane = PageDisplayer.choosePage(pageCode)
      splitPane.items.remove(1)
      splitPane.items.add(1, centerPane)
    }
  }

  val scrollPane = new ScrollPane {
    minWidth = 200
    maxWidth = 200
    fitToWidth = true
    fitToHeight = true
    id = "page-tree"
    content = controlsView
  }
  val splitPane = new SplitPane {
    dividerPositions = 0
    id = "page-splitpane"
    items.addAll(scrollPane, centerPane)
  }

  //
  // Layout the main stage
  //
  stage = new PrimaryStage {
    title = "ScalaFX Ensemble"
    scene = new Scene(1020, 700) {
      root = new BorderPane {
        top = new VBox {
          vgrow = Priority.ALWAYS
          hgrow = Priority.ALWAYS
          content = new ToolBar {
            prefHeight = 76
            maxHeight = 76
            id = "mainToolBar"
            content = List(
              new ImageView {
                image = new Image(
                  this.getClass.getResourceAsStream("/scalafx/ensemble/images/logo.png"))
                margin = Insets(0, 0, 0, 10)
              },
              new Region {
                minWidth = 300
              },
              new Button {
                minWidth = 120
                minHeight = 66
                id = "newButton"
              })
          }
        }
        center = new BorderPane {
          center = splitPane
        }
        styleClass += "application"
      }
    }
    scene().stylesheets += this.getClass.getResource("/scalafx/ensemble/css/ensemble.css").toExternalForm
  }
}
*/