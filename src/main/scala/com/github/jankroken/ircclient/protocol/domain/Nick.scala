package com.github.jankroken.ircclient.protocol.domain

case class Nick (val name: String) extends Target {
	require (name != null)
}
