package com.github.jankroken.ircclient.protocol.domain

case class NameList(channelType: String, val channel: Channel, nicks: String) extends ServerMessage {
  
  val secret = "@".equals(channelType)
  val privateChannel = "*".equals(channelType)
  
  val nicklist = nicks.split(" ")
  
  override def toString:String = {
	  val secretString = if (secret) "[secret]" else ""
	  val privateString = if (privateChannel) "[private]" else ""
	  val sb = new StringBuilder
	  sb.append("NameList("+channel+secretString+privateString+",")
	  var first = true
	  for (nick <- nicklist) {
	      if (first) {
	    	  sb.append(' ')
	    	  first = false
	      } else {
	    	  sb.append(',')
	      }
	      sb.append(nick)
	  }
	  sb.append(")")
	  sb.toString
  }
}
