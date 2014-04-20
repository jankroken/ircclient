package com.github.jankroken.ircclient.actors

import akka.actor.{ActorLogging, Actor}
import com.github.jankroken.ircclient.domain.{ChannelTarget, NetworkTarget}
import com.github.jankroken.ircclient.commands._
import com.github.jankroken.ircclient.commands.IdentifiedCommand.CTCPAction
import com.github.jankroken.ircclient.commands.JoinCommand
import com.github.jankroken.ircclient.domain.ChannelTarget
import com.github.jankroken.ircclient.commands.Server
import com.github.jankroken.ircclient.commands.IdentifiedCommand.CTCPAction
import com.github.jankroken.ircclient.domain.NetworkTarget
import com.github.jankroken.ircclient.commands.Text

class TargetActor  extends Actor with ActorLogging {

  def receive = {
    case target:NetworkTarget ⇒ context.become(addressing(target))
    case target:ChannelTarget ⇒ context.become(addressing(target))
    case server:IdentifiedCommand.Server ⇒ sender ! Server(new NetworkTarget(server.server))
    case other ⇒ println(s"No target selected: $other")
  }

  def addressing(target:NetworkTarget):Receive = {
    case target:NetworkTarget ⇒ context.become(addressing(target))
    case target:ChannelTarget ⇒ context.become(addressing(target))
    case text:IdentifiedCommand.Text ⇒ println(s"no channel selectet, can't send $text")
    case join:IdentifiedCommand.Join ⇒ sender ! JoinCommand(new ChannelTarget(target.name,join.channel))
    case server:IdentifiedCommand.Server ⇒ sender ! Server(new NetworkTarget(server.server))
    case other ⇒ println(s"TargetActor.addressing[Network] $other")
  }
  def addressing(target:ChannelTarget):Receive = {
    case target:NetworkTarget ⇒ context.become(addressing(target))
    case target:ChannelTarget ⇒ context.become(addressing(target))
    case text:IdentifiedCommand.Text ⇒ sender ! Text(target,text.param)
    case join:IdentifiedCommand.Join ⇒ sender ! JoinCommand(ChannelTarget(target.network,join.channel))
    case server:IdentifiedCommand.Server ⇒ sender ! Server(new NetworkTarget(server.server))
    case action:CTCPAction => sender ! CTCPActionCommand(target,action.message)
    case other ⇒ println(s"TargetActor.addressing[Channel] $other")
  }
}
