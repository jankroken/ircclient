package com.github.jankroken.ircclient.domain

sealed abstract class Event
abstract class GUIEvent extends Event
case class ChannelSelected(network:String,channel:String) extends GUIEvent
case class NetworkSelected(network:String) extends GUIEvent
