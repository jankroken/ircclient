package com.github.jankroken.ircclient.protocol.domain

import com.github.jankroken.ircclient.protocol.LowLevelServerMessage

trait LowLevelMessageListener {
	def onMessage(message: LowLevelServerMessage)
}
