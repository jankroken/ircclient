package com.github.jankroken.ircclient.domain

class EventListener {
  def onEvent(event: Event) = event match {
    case LineEntered(line) ⇒ {
        println(s"command entered: $line")
    }
    case _ ⇒ println(s"event:: $event")

  }
}
