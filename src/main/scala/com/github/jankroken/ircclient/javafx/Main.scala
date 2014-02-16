package com.github.jankroken.ircclient.javafx

import com.github.jankroken.ircclient.domain.{EventListener}
import javafx.application.Application
import javafx.scene.control.SplitPane
import javafx.geometry.Orientation
import javafx.stage.Stage
import javafx.scene.{Group, Scene}
import javafx.scene.layout.BorderPane
import com.github.jankroken.ircclient.gui.ChatMessageFactory

class Main extends Application {

  val eventListener = new EventListener
  val nickPane = NickPane(eventListener)
  val channelPane = ChannelPane(eventListener)
  val sidePanel = new SplitPane() {
    setId("list-splitpane")
    setDividerPosition(1,0)
    setMinWidth(120)
    setMaxWidth(300)
    setPrefWidth(200)
    setOrientation(Orientation.VERTICAL)
    setManaged(true)
    SplitPane.setResizableWithParent(this,true)
  }


//  val ob = ObservableBuffer[HBox]()
  val chatPane = ChatPane(new EventListener)

  def init(primaryStage:Stage) {
    primaryStage.setTitle("IRC Client")
    val root = new Group()
    val borderPane = new BorderPane()
    root.getChildren().add(borderPane)
    primaryStage.setScene(new Scene(root,1020,700))
    val center = new SplitPane() {
      setId("page-splitpane")
      getItems.addAll(sidePanel,chatPane)
      setDividerPosition(0,100)
//      setMaxHeight(10000)
//      setMaxWidth(10000)
//      setPrefHeight(10000)
//      setPrefWidth(10000)
      setManaged(true)
    }
    borderPane.setCenter(center)
    borderPane.setBottom(new CommandLine(eventListener))
    borderPane.setManaged(true)
    borderPane.setPrefHeight(1000)
  }

  def start(primaryStage:Stage) {
    init(primaryStage)
    println("hello")
    primaryStage.show()
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

    if (n > 10 && n < 99) chatPane.chatPanel.getChildren.remove(n)

  }}


}

object Main {
  def main(args: Array[String]) {
    Application.launch(classOf[Main], args: _*)
  }
}
