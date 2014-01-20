package com.github.jankroken.ircclient.protocol.domain;

class Channel(val name: String, val server: IRCServer) extends Target {

	var topic: Option[String] = None
  
	require(isAValidName(name))

	override def toString:String = name
	
	override def equals(o : Any):Boolean = {
		return ((o.isInstanceOf[Channel]) && name.equals(o.toString()))
	}
	
	override def hashCode:Int = name.hashCode

  private def isEmpty(s:String) = s == null || s.length() < 1

	def isAValidName(name: String):Boolean = {
	  if (isEmpty(name)) return false
		if (name.indexOf(' ') >= 0) return false
		if (name.indexOf(7) >= 0) return false; // check cast, 7 -> char
		if (name.indexOf(',') >= 0) return false
		if (name.length() > 50) return false
    if (name(0) == '#') return true
		if (name(0) == '&') return true
		if (name(0) == '+') return true
		if (name(0) == '!') return true
		return false
	}

	def setTopic(topic: String) = {
		println(this+": Topic set to: "+topic)
		this.topic = Some(topic)
	}

	def setTopicSetter(topicSetter: String) {
		println(this+": Topic set by: "+topicSetter)
	}
 
	def setTopicTime(topicTime: String) {
		println(this+": Topic set at: "+topicTime)
	}
  
	def clearTopic {
		this.topic = None
	}

	def userJoined(origin: Option[Origin]) {
		println(this+": User joined: "+origin)
	}
 
	def userLeft(origin: Option[Origin]) {
	    println(this+": User left: "+origin)
    }
}

object Channel {
  def apply(name:String, server: IRCServer) = new Channel(name,server)
}
