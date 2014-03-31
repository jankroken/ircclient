package com.github.jankroken.ircclient.commands

import com.github.jankroken.ircclient.domain.{NetworkTarget, ChannelTarget}

sealed abstract class Command
case class JoinCommand(target:ChannelTarget) extends Command
case class TextCommand(target:ChannelTarget,message:String) extends Command