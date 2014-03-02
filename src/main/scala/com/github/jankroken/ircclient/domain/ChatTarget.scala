package com.github.jankroken.ircclient.domain

abstract class ChatTarget
case class NetworkTarget(name:String) extends ChatTarget
case class ChannelTarget(network:String,channel:String) extends ChatTarget