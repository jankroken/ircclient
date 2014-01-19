package com.googlecode.estuary.sirc.domain

case class Nick (val name: String) extends Target {
	require (name != null)
}
