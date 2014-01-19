package com.github.jankroken.ircclient.protocol.domain

case class NickAlreadyInUse(nick: Nick) extends ServerMessage
