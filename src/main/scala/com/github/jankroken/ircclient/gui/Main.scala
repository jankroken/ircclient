package com.github.jankroken.ircclient.gui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Orientation
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.Scene
import scalafx.collections.ObservableBuffer
import scalafx.Includes._
import com.github.jankroken.ircclient.domain.{EventListener}

object Main extends JFXApp {


  val sidePanel = new SplitPane {
    minWidth = 120
    maxWidth = 300
    prefWidth = 200
    dividerPositions = 1
    orientation = Orientation.VERTICAL

    id = "list-splitpane"
    val eventListener = new EventListener
    items.addAll(NickPane(eventListener),ChannelPane(eventListener))
  }

  val ob = ObservableBuffer[HBox]()

  val chatPane = ChatPane(new EventListener)
  stage = new PrimaryStage {
    title = "IRC Client"
    scene = new Scene(1020, 700) {
      root = new BorderPane {
//        center =  new SplitPane {
//          dividerPositions = 0
//          id = "page-splitpane"
//          items.addAll(sidePanel,chatPane)
//        }
        bottom = new CommandLine(new EventListener)
      }
    }
  }

  val sampleChatLines = ChatMessageFactory.sampleChatLines
  val sampleChatLines2 = ChatMessageFactory.sampleChatLines2
  val sampleImage = ChatMessageFactory.sampleImage

  List.range(0,999).foreach{n => {

    if (n != 997) {
      chatPane.chatPanel.add(sampleChatLines2(n).from,0,n)
      chatPane.chatPanel.add(sampleChatLines2(n).message,1,n)
    } else {
      chatPane.chatPanel.add(sampleImage,0,n,2,1)
    }

    if (n > 10 && n < 99) chatPane.chatPanel.children.remove(n)

  }}

}
