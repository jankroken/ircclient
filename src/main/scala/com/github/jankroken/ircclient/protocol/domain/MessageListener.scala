package com.github.jankroken.ircclient.protocol.domain

trait MessageListener {
	def onMessage(message: ServerMessage)
}
