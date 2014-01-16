package com.github.jankroken.ircclient.domain

class EventListener {
  def onEvent(event: Event) = event match {
    case _ => println(event)
  }
}
