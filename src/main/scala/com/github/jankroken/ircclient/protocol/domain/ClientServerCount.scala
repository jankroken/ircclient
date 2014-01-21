package com.github.jankroken.ircclient.protocol.domain

class ClientServerCount(val clientServerString: String) extends ServerMessage {
	var clients: Option[Int] = None
	var servers: Option[Int] = None
	private val ClientServer = "I have ([0-9][0-9]*) clients and ([0-9][0-9]*) servers".r
	clientServerString match {
	  case ClientServer(clientCount, serverCount) => 
	  	clients = Some(clientCount.toInt)
	  	servers = Some(serverCount.toInt)
	  case _ =>
	}

	override def toString = clients match {
    case None => "ClientServerCount(unrecognized, string="+clientServerString+")"
    case Some(clientCount) =>
      servers match {
        case Some(serverCount) => "ClientServerCount("+clients+","+servers+")"
        case _ => "ClientServerCount(internal error)"
      }
		}

}
