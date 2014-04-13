package com.github.jankroken.ircclient.protocol.domain

class CTCPServerMessage extends ServerMessage
case class CTCPAction(origin: Option[Origin],targets: Array[Target],message:String) extends CTCPServerMessage
case class CTCPVersionQuery(origin:Origin)
case class CTCPVersionResponse(target:Target,version:String)
