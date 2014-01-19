package com.github.jankroken.ircclient.protocol.domain

class ServerParameters(arguments: Array[String]) extends ServerMessage {

    override def toString:String = {
      	val sb = new StringBuilder
      	sb.append("ServerParameters(")
        for (argument <- arguments) {
          sb.append(" "+argument)
        }
        sb.append(")")
        sb.toString
    }
  
  
}
