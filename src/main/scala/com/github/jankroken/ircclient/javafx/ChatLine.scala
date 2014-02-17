package com.github.jankroken.ircclient.javafx

import scalafx.scene.control.Label

abstract class ChatLine
case class SimpleMessage(from:Label,message:Label)
