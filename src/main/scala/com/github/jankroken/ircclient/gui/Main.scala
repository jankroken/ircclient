package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{EventListener}
import javafx.application.Application
import javafx.scene.control.SplitPane
import javafx.geometry.Orientation
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.layout.BorderPane

class Main extends Application {

  val eventListener = new EventListener
  val nickPane = NickPane(eventListener)
  val channelPane = ChannelPane(eventListener)
  val sidePanel = new SplitPane() {
    setId("list-splitpane")
//    setDividerPosition(1,0)
    setMinWidth(120)
    setMaxWidth(300)
    setPrefWidth(200)
    setOrientation(Orientation.VERTICAL)
    getItems().addAll(nickPane,channelPane)
  }


//  val ob = ObservableBuffer[HBox]()
  val testChatPane = ChatPane(new EventListener)
  val doomPane = ChatPane(new EventListener)
  val ocamlPane = ChatPane(new EventListener)

  def init(primaryStage:Stage) {
    val borderPane = new BorderPane()
    primaryStage.setTitle("IRC Client")
    primaryStage.setScene(new Scene(borderPane,1020,700))

    val center = new SplitPane() {
      setId("page-splitpane")
      getItems.addAll(sidePanel,testChatPane)
      getItems().set(1,doomPane)
      setDividerPosition(0,0.2)
    }
    borderPane.setCenter(center)
    borderPane.setBottom(new CommandLine(eventListener))
  }

  def start(primaryStage:Stage) {
    init(primaryStage)
    println("hello")
    primaryStage.show()
  }

//  val sampleChatLines = ChatMessageFactory.sampleChatLines
  val sampleChatLines2 = ChatMessageFactory.sampleChatLines2
  val sampleImage = ChatMessageFactory.sampleImage

  List.range(0,999).foreach{n => {

    if (n != 997) {
      testChatPane.chatPanel.add(sampleChatLines2(n).from,0,n)
      testChatPane.chatPanel.add(sampleChatLines2(n).message,1,n)
    } else {
      testChatPane.chatPanel.add(sampleImage,0,n,2,1)
    }

    if (n > 10 && n < 99) testChatPane.chatPanel.getChildren.remove(n)

  }}
  doomPane.chatPanel.add(sampleImage,0,0,2,1)

}

object Main {
  def main(args: Array[String]) {
    Application.launch(classOf[Main], args: _*)
  }
}