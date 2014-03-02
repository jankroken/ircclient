package com.github.jankroken.ircclient.protocol

import com.github.jankroken.ircclient.protocol.domain.{Channel, IRCServer, UserMode, User}

object DalNetListener extends App {
  testInitialContact

  def testInitialContact() {

    val xenobot1 = "xenobot1"
    object xeno extends User {
      val nick = xenobot1
      val realName = "Jan Kroken"
      val userName = xenobot1
      val userMode = new UserMode()
    }
    val dalnet = new IRCServer("irc.dal.net")
    dalnet.user = xeno
    dalnet.connect(None)
    Thread.sleep(2000)
    dalnet.setNick(xenobot1)
    Thread.sleep(2000)
    dalnet.logon
    Thread.sleep(4000)
    var java: Channel = dalnet.join("#java")
    var norge: Channel = dalnet.join("##programming")
    Thread.sleep(2000000)
  }

}