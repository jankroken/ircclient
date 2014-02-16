package com.github.jankroken.ircclient.javafx

import com.github.jankroken.ircclient.domain.{EventListener, NetworkSelected, ChannelSelected}
import javafx.scene.control.{TreeView, TreeItem, ScrollPane}
import javafx.scene.layout.Priority


class ChannelPane(val eventListener:EventListener) extends ScrollPane {

  def ncChildren(network:String,channels:List[String]) =
    new TreeItem[String](network) {
      setExpanded(true)
//      getChildren.addAll(channels.map(new TreeItem(_)))
    }
  setFitToHeight(true)
  setFitToWidth(true)

/*
  def networksChannels =
    new TreeView[String]{
      setRoot(new TreeItem[String]("networks") {
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
        eventListener.onEvent(selected)
      }
      setVgrow(Priority.ALWAYS)
    }
*/
  /*
  fitToWidth = true
    fitToHeight = true
    minHeight = 200
    prefHeight = 2000

    id = "page-tree"

    content = networksChannels
    */
}

object ChannelPane {
  def apply(eventListener:EventListener) = new ChannelPane(eventListener)
}
