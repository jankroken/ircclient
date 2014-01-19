package com.googlecode.estuary.sirc.domain

import java.util.Date

case class TopicSetBy(val channel: Channel, userString: String, timestampString: String) extends ServerMessage {
	 val date = new Date()
	 override def toString:String = {
		"TopicSetBy("+userString+","+timestampString+")"
	 }
}
