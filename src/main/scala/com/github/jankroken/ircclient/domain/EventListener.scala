package com.github.jankroken.ircclient.domain

class EventListener {
  def onEvent(event: Event) {
    println(event)
  }
}
