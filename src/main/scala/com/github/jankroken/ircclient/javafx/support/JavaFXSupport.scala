package com.github.jankroken.ircclient.javafx.support

import javafx.scene.control.TreeItem

object JavaFXSupport {
  implicit class TreeItemMethods[T](treeItem: TreeItem[T]) {
    def hasGrandParent = treeItem.getParent.getParent != null
    def parent = treeItem.getParent
  }
}
