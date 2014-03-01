package com.github.jankroken.ircclient.gui

import javafx.scene.control.Label

abstract class ChatLine
case class SimpleMessage(from:Label,message:Label)
