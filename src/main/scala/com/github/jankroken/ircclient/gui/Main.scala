package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{NetworkTarget, ChannelTarget, ChatTarget, EventListener}
import javafx.application.Application
import javafx.scene.control.SplitPane
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import com.github.jankroken.ircclient.actors.IRCActorSystem

class Main extends Application {

  val eventListener = new EventListener(setChatPane(_))
  val chatPanels = new ChatPanels(eventListener)
  val nickPanes = new NickPanes(eventListener)
  val nickPane = NickPane(eventListener)
  val channelPane = ChannelPane(eventListener)
  val sidePanel = new SidePanel
  sidePanel.getItems.addAll(nickPane,channelPane)



  //  val ob = ObservableBuffer[HBox]()
  val testChatPane = chatPanels.getPanel(NetworkTarget("freenode"))
  val doomPane = chatPanels.getPanel(ChannelTarget("quakenet","#doom"))
  val ocamlPane = chatPanels.getPanel(ChannelTarget("efnet","#ocaml"))

  val borderPane = new BorderPane()
  val center = new SplitPane() {
    setId("page-splitpane")
    getItems.addAll(sidePanel,testChatPane)
    getItems.set(1,doomPane)
    setDividerPosition(0,0.2)
  }

  def init(primaryStage:Stage) {
    primaryStage.setTitle("IRC Client")
    primaryStage.setScene(new Scene(borderPane,1020,700))
    primaryStage.getScene().getStylesheets().add("ircclient.css");


    borderPane.setCenter(center)
    borderPane.setBottom(new CommandLine(eventListener))
  }

  def start(primaryStage:Stage) {
    init(primaryStage)
    println("hello")
    primaryStage.show()
    IRCActorSystem.main ! chatPanels
    IRCActorSystem.main ! nickPanes
  }

//  val sampleChatLines = ChatMessageFactory.sampleChatLines
  val sampleChatLines2 = ChatMessageFactory.sampleChatLines2
  val sampleImage = ChatMessageFactory.sampleImage

  doomPane.chatPanel.add(sampleImage,0,0,2,1)

  def setChatPane(target:ChatTarget) {
    center.getItems.set(1,chatPanels.getPanel(target))
    sidePanel.getItems.set(0,nickPanes.getPanel(target))
  }
  def activeChatPane = center.getItems.get(1)
}

object Main {
  def main(args: Array[String]) {
    Application.launch(classOf[Main], args: _*)
  }
}
