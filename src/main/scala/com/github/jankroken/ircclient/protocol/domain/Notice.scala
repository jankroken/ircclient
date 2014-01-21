package com.github.jankroken.ircclient.protocol.domain

case class Notice(origin: Option[Origin], targets: Array[Target], message: String ) extends ServerMessage {
  
  override def toString:String = {
    val sb = new StringBuffer(100)
    origin match {
      case Some(origin) => sb.append('(').append(origin).append(") ")
      case None =>
    }
    sb.append("<Notice ")
    var first = true
    for (target ← targets) {
    	if (first) {
    	  sb.append(target.toString)
    	  first = false
    	} else {
    	  sb.append(',').append(target.toString)
    	}
    }
    sb.append(" : ").append(message).append(">")
    sb.toString
  }
  
}
