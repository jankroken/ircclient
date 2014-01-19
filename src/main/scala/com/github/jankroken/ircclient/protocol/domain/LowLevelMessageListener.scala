package com.googlecode.estuary.sirc.domain

import com.googlecode.estuary.sirc.protocol.LowLevelServerMessage

trait LowLevelMessageListener {
	def onMessage(message: LowLevelServerMessage)
}
