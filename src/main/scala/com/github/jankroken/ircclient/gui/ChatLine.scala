package com.github.jankroken.ircclient.gui

import scalafx.scene.control.Label

abstract class ChatLine
case class SimpleMessage(from:Label,message:Label)
