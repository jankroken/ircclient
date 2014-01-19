package com.googlecode.estuary.sirc.domain

case class ChangeNick(origin: Option[Origin], newNick: String) extends ServerMessage {

}
