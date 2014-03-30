package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain._
import com.github.jankroken.ircclient.gui.support.JavaFXSupport._
import javafx.scene.control.{TreeView, TreeItem, ScrollPane}
import javafx.scene.layout.Priority
import javafx.beans.value.{ObservableValue, ChangeListener}
import com.github.jankroken.ircclient.domain.ChannelTarget
import com.github.jankroken.ircclient.domain.NetworkSelected
import scala.Some
import com.github.jankroken.ircclient.domain.ChannelSelected


class ChannelPane(val eventListener: EventListener) extends ScrollPane {

  def ncChildren(network: String, channels: List[String]) =
    new TreeItem[String](network) {
      setExpanded(true)
      channels.map(new TreeItem(_)).foreach {
        getChildren.add(_)
      }
    }

  val networksRoot = new TreeItem[String]("networks") {
    setVisible(true)
    getProperties.put("vgrow", Priority.ALWAYS)
    setExpanded(true)
//    getChildren.addAll(freenodeChannels,efnetChannels,quakenetChannels)
  }
  def networksChannels =
    new TreeView[String] {
      setShowRoot(false)
      setRoot(networksRoot)
      getSelectionModel.selectedItemProperty().addListener(channelListener)


      object channelListener extends ChangeListener[TreeItem[String]] {
        def changed(v:ObservableValue[_ <: TreeItem[String]], oldValue:TreeItem[String], newValue:TreeItem[String]) {
          if (newValue != null) {
            if (newValue.hasGrandParent)
              eventListener.onEvent(ChannelSelected(newValue.getParent.getValue, newValue.getValue))
            else
              eventListener.onEvent(NetworkSelected(newValue.getValue))
          }
        }
      }
    }

  setFitToWidth(true)
  setFitToHeight(true)
  setMinHeight(200)
  setPrefHeight(2000)
  setId("page-tree")
  setContent(networksChannels)


  def networkEntries = networksChannels.getRoot.getChildren.toArray
    .filter(_.isInstanceOf[TreeItem[String]])
    .map(_.asInstanceOf[TreeItem[String]])
  def networkEntry(network:String):Option[TreeItem[String]] = networkEntries.find(_.getValue == network)


  def addNetwork(networkName:String) = {
    val item = new TreeItem[String](networkName) {
      setExpanded(true)
      // getChildren.add(new TreeItem[String]("#bogus"))
    }
    networksChannels.getRoot.getChildren.addAll(item)
    item
  }

  def findOrAddNetworkEntry(network:String):TreeItem[String] = {
    networkEntry(network) match {
      case None => addNetwork(network)
      case Some(treeItem) => treeItem.asInstanceOf[TreeItem[String]]
    }
  }

  def channelEntries(networkEntry:TreeItem[String]) = networkEntry.getChildren.toArray
    .filter(_.isInstanceOf[TreeItem[String]])
    .map(_.asInstanceOf[TreeItem[String]])
  def channelEntry(networkEntry:TreeItem[String],channel:String):Option[TreeItem[String]] =
      channelEntries(networkEntry).find(_.getValue == channel)

  def addChannel(networkEntry:TreeItem[String],channelName:String) {
    Thread.sleep(50)
    val item = new TreeItem[String](channelName)
    networkEntry.getChildren.addAll(item)
    item
  }


  def findOrAddChannelEntry(networkEntry:TreeItem[String],channel:String) = {
    val channelEntry = channelEntries(networkEntry).find(_.getValue == channel)
    channelEntry match {
      case None => addChannel(networkEntry,channel)
      case Some(treeItem) => treeItem
    }
  }


  def addOrModifyChannel(target:ChannelTarget) {
    val networkEntry = findOrAddNetworkEntry(target.network)
    val channelEntry = findOrAddChannelEntry(networkEntry,target.channel)
  }

  def addOrModifyNetwork(target:NetworkTarget) {
    val networkEntry = findOrAddNetworkEntry(target.name)
  }


}

object ChannelPane {
  def apply(eventListener: EventListener) = new ChannelPane(eventListener)
}
