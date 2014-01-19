package com.googlecode.estuary.sirc

import com.googlecode.estuary.sirc.domain.Channel
import com.googlecode.estuary.sirc.domain.IRCServer
import com.googlecode.estuary.sirc.domain.User
import com.googlecode.estuary.sirc.domain.UserMode

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
    dalnet.connect
    Thread.sleep(2000)
    dalnet.setNick(xenobot1)
    Thread.sleep(2000)
    dalnet.logon
    Thread.sleep(4000)
    var java: Channel = dalnet.join("#java")
    var norge: Channel = dalnet.join("#norge")
    Thread.sleep(2000000)
  }

}