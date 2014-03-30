package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.{NetworkTarget, ChannelTarget}

abstract class AddToTreeView
case class AddChannelToTreeView(target:ChannelTarget) extends AddToTreeView
case class AddNetworkToTreeView(target:NetworkTarget) extends AddToTreeView

