package com.github.jankroken.ircclient.domain

/**
 * Created by jan on 08/01/14.
 */
abstract class GUIEvent
case class ChannelSelected(network:String,channel:String) extends GUIEvent