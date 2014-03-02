package com.github.jankroken.ircclient.domain

import com.github.jankroken.ircclient.commands.IdentificationParser

class EventListener {
  def onEvent(event: Event) = event match {
    case LineEntered(line) ⇒ {
      val ip = new IdentificationParser
      val command = ip.parseCommand(line)
      println(s"command entered: $command")
    }
    case _ ⇒ println(s"event:: $event")

  }
}
