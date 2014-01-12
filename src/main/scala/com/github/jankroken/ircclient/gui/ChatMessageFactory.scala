package com.github.jankroken.ircclient.gui

import scalafx.scene.layout.HBox
import scalafx.scene.control.Label
import scalafx.scene.paint.Color
import scalafx.scene.image.{Image, ImageView}

object ChatMessageFactory {

  def sampleChatLines = List.range(1,1000).map(num => new HBox {
    content = List(new Label {
      text = s"Hello$num"
      minWidth = 100
      maxWidth = 140
      prefWidth = 100
      textFill = Color.BLUE
    }, new Label { text= s"Hello $num"})
  })

  def sampleChatLines2 = List.range(1,1000).map(num =>

    SimpleMessage(new Label {
      text = s"Hello$num"
      minWidth = 100
      maxWidth = 140
      prefWidth = 100
      textFill = Color.BLUE
    }, new Label { text= s"Hello $num"})
  )

  def sampleImage = {
    val url = this.getClass.getClassLoader.getResource("mobius_building.jpeg").toExternalForm
    new ImageView(new Image(url, requestedWidth = 300, requestedHeight = 120, preserveRatio = true, smooth = true))
  }

}
