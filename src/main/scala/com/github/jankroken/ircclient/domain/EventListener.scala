package com.github.jankroken.ircclient.domain

import com.github.jankroken.ircclient.commands.{IdentifiedCommand, IdentificationParser}
import com.github.jankroken.ircclient.actors.IRCActorSystem

class EventListener(setActiveTarget:(ChatTarget) ⇒ Unit) {
  def onEvent(event: Event) = event match {
    case LineEntered(line) ⇒
      val ip = new IdentificationParser
      val command = IdentifiedCommand.from(ip.parseCommand(line))
      IRCActorSystem.main ! command
    case NetworkSelected(network) ⇒
      IRCActorSystem.main ! NetworkTarget(network)
      setActiveTarget(NetworkTarget(network))
    case ChannelSelected(network,channel) ⇒
      IRCActorSystem.main ! ChannelTarget(network,channel)
      setActiveTarget(ChannelTarget(network,channel))
    case _ ⇒ println(s"event:: $event")
  }
}
