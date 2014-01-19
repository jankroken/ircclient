package com.github.jankroken.ircclient.protocol.domain

case class ChangeNick(origin: Option[Origin], newNick: String) extends ServerMessage {

}
