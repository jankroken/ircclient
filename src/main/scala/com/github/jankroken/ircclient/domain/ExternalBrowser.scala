package com.github.jankroken.ircclient.domain

import java.net.URI
import com.github.jankroken.ircclient.gui.Main

object ExternalBrowser {

  var openMethod:String=>Unit = null

  def setOpenMethod(method:String=>Unit) {
    openMethod = method
  }

  def open(url:String) {
    val uri = new URI(url)
    // Main.getHostServices.showDocument()
    openMethod(url)
   // java.awt.Desktop.getDesktop().browse(uri)
  }

  /*
  def main(args:Array[String]) {
    open("http://www.vg.no/")
  }
  */
}
