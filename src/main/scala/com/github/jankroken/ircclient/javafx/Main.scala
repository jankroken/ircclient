package com.github.jankroken.ircclient.javafx

import com.github.jankroken.ircclient.domain.{EventListener}
import javafx.event.EventHandler
import javafx.application.Application
import javafx.scene.control.{SplitPane, SplitPaneBuilder}
import javafx.geometry.Orientation
import javafx.stage.Stage
import javafx.scene.{Group, Scene}
import javafx.scene.layout.BorderPane
import com.github.jankroken.ircclient.gui.ChatMessageFactory

object Main extends Application {


  //val sidePanel = SplitPaneBuilder.create().id("list-splitpane")   .build()
  val sidePanel = new SplitPane()
  sidePanel.setId("list-splitpane")
  sidePanel.getItems().addAll(NickPane(eventListener),ChannelPane(eventListener))
  sidePanel.setDividerPosition(1,0)



    // .items(NickPane(eventListener),ChannelPane(eventListener))
    // dividerPosition(1).build()

//
  val eventListener = new EventListener

/*
    minWidth = 120
    maxWidth = 300
    prefWidth = 200
    dividerPositions = 1
    orientation = Orientation.VERTICAL
     */


//  val ob = ObservableBuffer[HBox]()
  val chatPane = ChatPane(new EventListener)

  def init(primaryStage:Stage) {
    primaryStage.setTitle("IRC Client")
    val root = new Group()
    val scene = new Scene(root)
    primaryStage.setScene(scene)
    val borderPane = new BorderPane()
    root.getChildren().add(borderPane)
//    bottom = new CommandLine(new EventListener)
    val center = new SplitPane()
    center.setId("page-splitpane")
    center.getItems.addAll(sidePanel,chatPane)
    // SplitPaneBuilder.create().id("page-splitpane").build()
    center.setDividerPosition(0,0)

  // .items(sidePanel,chatPane).dividerPositions(0)
  }

  def start(primaryStage:Stage) {
    init(primaryStage)
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
