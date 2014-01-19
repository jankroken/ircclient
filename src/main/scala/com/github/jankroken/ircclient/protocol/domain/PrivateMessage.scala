package com.github.jankroken.ircclient.protocol.domain

case class PrivateMessage(val origin: Option[Origin],val  targets: Array[Target],val message:String) extends ServerMessage {
  
  override def toString:String = {
    val sb = new StringBuffer(100)
    origin match {
      case Some(origin) => sb.append('(').append(origin).append(") ")
      case None =>
    }

    sb.append("<PrivateMessage ")
    var first = true
    for (target <- targets) {
    	if (first) {
    	  sb.append(target.toString)
    	  first = false;
    	} else {
    	  sb.append(',')
          sb.append(target.toString)
    	}
    }
    sb.append(" : ")
    sb.append(message)
    sb.append(">")
    sb.toString
  }

}
