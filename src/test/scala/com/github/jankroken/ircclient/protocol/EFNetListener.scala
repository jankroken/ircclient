package com.github.jankroken.ircclient.protocol

import com.github.jankroken.ircclient.protocol.domain.{Channel, IRCServer, UserMode, User}

object EFNetListener extends App {
  testInitialContact

  def testInitialContact() {

    val xenobot1 = "xenobot1"
    object xeno extends User {
      val nick = xenobot1
      val realName = "Jan Kroken"
      val userName = xenobot1
      val userMode = new UserMode()
    }
    val efnet = new IRCServer("irc.efnet.org")
    efnet.user = xeno
    efnet.connect(None)
    Thread.sleep(2000)
    efnet.setNick(xenobot1)
    Thread.sleep(2000)
    efnet.logon
    Thread.sleep(4000)
    var java: Channel = efnet.join("#java")
    var norge: Channel = efnet.join("#norge")
    Thread.sleep(2000000)
    //		freenode.message(xenotest,"hello world");
    //		Thread.sleep(2000);
    //		freenode.quit("sayonara");
    //		println("FOO");
  }

}