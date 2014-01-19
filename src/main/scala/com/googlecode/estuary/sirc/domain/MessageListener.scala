package com.googlecode.estuary.sirc.domain

trait MessageListener {
	def onMessage(message: ServerMessage)
}
