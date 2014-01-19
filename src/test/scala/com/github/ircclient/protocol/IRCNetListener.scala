package com.googlecode.estuary.sirc

import com.googlecode.estuary.sirc.domain.Channel
import com.googlecode.estuary.sirc.domain.IRCServer
import com.googlecode.estuary.sirc.domain.User
import com.googlecode.estuary.sirc.domain.UserMode

object IRCNetListener extends App {
  testInitialContact

  def testInitialContact() {

    val xenobot1 = "xenobot1"
    object xeno extends User {
      val nick = xenobot1
      val realName = "Jan Kroken"
      val userName = xenobot1
      val userMode = new UserMode()
    }
    val ircnet = new IRCServer("irc.ifi.uio.no")
    ircnet.user = xeno
    ircnet.connect
    Thread.sleep(2000)
    ircnet.setNick(xenobot1)
    Thread.sleep(2000)
    ircnet.logon
    Thread.sleep(4000)
    var java: Channel = ircnet.join("#java")
    var norge: Channel = ircnet.join("#norge")
    Thread.sleep(2000000)
    //		freenode.message(xenotest,"hello world");
    //		Thread.sleep(2000);
    //		freenode.quit("sayonara");
    //		println("FOO");
  }

}