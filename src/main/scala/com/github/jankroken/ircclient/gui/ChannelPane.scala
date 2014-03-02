package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{NetworkSelected, ChannelSelected, EventListener}
import com.github.jankroken.ircclient.gui.support.JavaFXSupport._
import javafx.scene.control.{TreeView, TreeItem, ScrollPane}
import javafx.scene.layout.Priority
import javafx.beans.value.{ObservableValue, ChangeListener}


class ChannelPane(val eventListener: EventListener) extends ScrollPane {

  def ncChildren(network: String, channels: List[String]) =
    new TreeItem[String](network) {
      setExpanded(true)
      //      getChildren.addAll(channels.map(new TreeItem(_)))
      channels.map(new TreeItem(_)).foreach {
        getChildren.add(_)
      }
    }


  val freenodeChannels = ncChildren("freenode", List("#scala", "#java", "#haskell"))
  val efnetChannels = ncChildren("efnet", List("#ocaml", "#prolog", "#ada"))
  val quakenetChannels = ncChildren("quakenet", List("#quake", "#doom", "#wolfenstein", "#keen", "#doom2", "#doom3", "quake2"))

  def networksChannels =
    new TreeView[String] {
      setRoot(new TreeItem[String]("networks") {
        setVisible(true)
        getProperties.put("vgrow", Priority.ALWAYS)
        setExpanded(true)
        setShowRoot(false)
        getChildren.addAll(freenodeChannels,efnetChannels,quakenetChannels)
        println(s"getSelectionModel⇒$getSelectionModel")
        println(s"getSelectionModel.getSelectedItem⇒${getSelectionModel.getSelectedItem}")


        object channelListener extends ChangeListener[TreeItem[String]] {
          def changed(v:ObservableValue[_ <: TreeItem[String]], oldValue:TreeItem[String], newValue:TreeItem[String]) {
            if(newValue.hasGrandParent)
              eventListener.onEvent(ChannelSelected(newValue.getParent.getValue,newValue.getValue))
            else
              eventListener.onEvent(NetworkSelected(newValue.getValue))
          }
        }
        getSelectionModel.selectedItemProperty().addListener(channelListener)
      })}

  setFitToWidth(true)
  setFitToHeight(true)
  setMinHeight(200)
  setPrefHeight(2000)
  setId("page-tree")
  setContent(networksChannels)
}

object ChannelPane {
  def apply(eventListener: EventListener) = new ChannelPane(eventListener)
}
