package com.github.jankroken.ircclient.commands

import com.github.jankroken.ircclient.domain.{NetworkTarget, ChannelTarget}

sealed abstract class Command
case class JoinCommand(target:ChannelTarget) extends Command
case class Text(target:ChannelTarget,message:String) extends Command
case class Server(target:NetworkTarget) extends Command
case class CTCPActionCommand(target:ChannelTarget,message:String) extends Command