package com.github.jankroken.ircclient.domain

import com.github.jankroken.ircclient.protocol.domain.Channel

case class NicksForChannel(channel:Channel,nicks:List[String])