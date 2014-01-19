package com.github.jankroken.ircclient.protocol.domain

import com.googlecode.estuary.sirc.protocol.LowLevelServerMessage

trait LowLevelMessageListener {
	def onMessage(message: LowLevelServerMessage)
}
