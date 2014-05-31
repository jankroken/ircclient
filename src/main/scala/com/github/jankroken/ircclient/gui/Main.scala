package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{NetworkTarget, ChannelTarget, ChatTarget, EventListener}
import javafx.application.Application
import javafx.scene.control.SplitPane
import javafx.stage.{WindowEvent, Stage}
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import com.github.jankroken.ircclient.actors.IRCActorSystem
import javafx.event.EventHandler

class Main extends Application {


  def initClojure { new clojure.lang.RT }

  var center:SplitPane = null
  var eventListener:EventListener = null
  var chatPanels:ChatPanels = null
  var nickPanes:NickPanes = null
  var nickPane:NickPane = null
  var sidePanel:SidePanel = null
  var testChatPane:ChannelPane = null


  def start(primaryStage:Stage) {

    def setChatPane(target:ChatTarget) {
      center.getItems.set(1,chatPanels.getPanel(target))
      sidePanel.getItems.set(0,nickPanes.getPanel(target))
    }
    val eventListener = new EventListener(setChatPane)
    chatPanels = new ChatPanels(eventListener)
    nickPanes = new NickPanes(eventListener)
    val nickPane = NickPane(eventListener)
    sidePanel = new SidePanel
    val testChatPane = chatPanels.getPanel(NetworkTarget("freenode"))

    center = new SplitPane() {
      setId("page-splitpane")
      getItems.addAll(sidePanel, testChatPane)
      setDividerPosition(0, 0.2)
    }


    val channelPane = ChannelPane(eventListener)
    sidePanel.getItems.addAll(nickPane, channelPane)

    val ocamlPane = chatPanels.getPanel(ChannelTarget("efnet", "#ocaml"))

    val borderPane = new BorderPane()

    primaryStage.setTitle("Glazed")
    primaryStage.setScene(new Scene(borderPane,1020,700))
    primaryStage.getScene.getStylesheets.add("ircclient.css");
    primaryStage.setOnCloseRequest(new EventHandler[WindowEvent] {
      @Override
      def handle(event: WindowEvent) {
        stop()
        println("exiting")
        System.exit(0)
      }
    })
    val sampleChatLines2 = ChatMessageFactory.sampleChatLines2
    val sampleImage = ChatMessageFactory.sampleImage

    def activeChatPane = center.getItems.get(1)


    borderPane.setCenter(center)
    borderPane.setBottom(new CommandLine(eventListener))

    primaryStage.show()
    IRCActorSystem.main ! chatPanels
    IRCActorSystem.main ! nickPanes
    IRCActorSystem.main ! channelPane
  }

}

object Main {
  def main(args: Array[String]) {
    Application.launch(classOf[Main], args: _*)
  }
}
