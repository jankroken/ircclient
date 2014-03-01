package com.github.jankroken.ircclient.javafx

import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.image.{Image, ImageView}

object ChatMessageFactory {

  def sampleChatLines = List.range(1,1000).map(num ⇒ new HBox {
    getChildren.addAll(new Label {
      setText(s"Hello$num")
      setMinWidth(100)
      setMaxWidth(140)
      setPrefWidth(100)
      setTextFill(Color.BLUE)
    }, new Label { setText(s"Hello $num")})
  })

  def sampleChatLines2 = List.range(1,1000).map(num ⇒

    SimpleMessage(new Label {
      setText(s"Hello$num")
      setMinWidth(100)
      setMaxWidth(140)
      setPrefWidth(100)
      setTextFill(Color.BLUE)
    }, new Label { setText(s"Hello $num")})
  )

  def sampleImage = {
    val url = this.getClass.getClassLoader.getResource("mobius_building.jpeg").toExternalForm
    new ImageView(new Image(url,300,120,true,true))
  }

}
