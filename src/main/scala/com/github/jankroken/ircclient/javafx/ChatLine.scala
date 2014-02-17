package com.github.jankroken.ircclient.javafx

import javafx.scene.control.Label

abstract class ChatLine
case class SimpleMessage(from:Label,message:Label)
