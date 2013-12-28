package com.github.jankroken.ircclient

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object Main extends JFXApp {
  stage = new PrimaryStage {
    title = "IRC Client"
    scene = new Scene(1020, 700) {
    }
  }
}
