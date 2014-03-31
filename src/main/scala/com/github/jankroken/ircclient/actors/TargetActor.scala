package com.github.jankroken.ircclient.actors

import akka.actor.{ActorLogging, Actor}
import com.github.jankroken.ircclient.domain.{SimpleMessage, ChannelTarget, NetworkTarget, ChatTarget}
import com.github.jankroken.ircclient.commands.{TextCommand, JoinCommand, IdentifiedCommand}
import com.github.jankroken.ircclient.gui.AddChannelToTreeView

class TargetActor  extends Actor with ActorLogging {

  def receive = {
    case target:NetworkTarget ⇒ context.become(addressing(target))
    case target:ChannelTarget ⇒ context.become(addressing(target))
    case other ⇒ println(s"TargetActor.receive $other")
  }

  def addressing(target:NetworkTarget):Receive = {
    case text:IdentifiedCommand.Text ⇒ {
      println(s"no channel selectet, can't send $text")
    }
    case join:IdentifiedCommand.Join ⇒ {
      sender ! JoinCommand(new ChannelTarget(target.name,join.channel))
    }
    case other ⇒ println(s"TargetActor.addressing[Network] $other")
  }
  def addressing(target:ChannelTarget):Receive = {
    case text:IdentifiedCommand.Text ⇒ {
      sender ! TextCommand(target,text.param)
    }
    case join:IdentifiedCommand.Join ⇒ {
      sender ! JoinCommand(ChannelTarget(target.network,join.channel))
    }
    case other ⇒ println(s"TargetActor.addressing[Channel] $other")
  }
}
