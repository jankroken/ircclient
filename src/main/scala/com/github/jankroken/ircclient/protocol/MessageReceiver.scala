package com.github.jankroken.ircclient.protocol

import java.io.BufferedReader
import com.github.jankroken.ircclient.protocol.domain.LowLevelMessageListener

class MessageReceiver(input: BufferedReader, messageListener: LowLevelMessageListener) extends Thread {

  this.start()

  override def run() {
    try {
      readLines()
    } catch {
      case ex: Exception =>
        println("Exception in run: " + ex)
        ex.printStackTrace()
    }
  }


  def readLines() = {
    var inputLine = input.readLine()
    while (inputLine != null) {
      val message = LowLevelServerMessage(inputLine)
      messageListener.onMessage(message)
      inputLine = input.readLine()
    }
    println("END OF INPUT. Exiting")
  }


}
