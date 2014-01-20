package com.github.jankroken.ircclient.protocol.domain

sealed trait Origin

case class Server(servername:String) extends Origin {
  override def toString = "Server("+servername+")"
}
case class NickAtHost(nick:String, host: String) extends Origin {
  override def toString = "NickAndUserAtHost(nick="+nick+",host="+host+")"
}
case class NickAndUserAtHost(nick:String, user: String, host: String) extends Origin {
  override def toString = "NickAndUserAtHost(nick="+nick+",user="+user+",host="+host+")"
}
case class UnspecifiedOrigin() extends Origin {
  override def toString = "UnspecifiedOrigin"
}
