package com.github.jankroken.ircclient.protocol

import com.github.jankroken.ircclient.protocol.domain.{Channel, IRCServer, UserMode, User}

object RizonNetListener extends App {
  testInitialContact

  def testInitialContact() {

    val xenobot1 = "xenobot1"
    object xeno extends User {
      val nick = xenobot1
      val realName = "Jan Kroken"
      val userName = xenobot1
      val userMode = new UserMode()
    }
    val rizon = new IRCServer("irc.rizon.net")
    rizon.user = xeno
    rizon.connect(None)
    Thread.sleep(2000)
    rizon.setNick(xenobot1)
    Thread.sleep(2000)
    rizon.logon
    Thread.sleep(4000)
    var java: Channel = rizon.join("#java")
    var norge: Channel = rizon.join("#norge")
    Thread.sleep(2000000)
    //		freenode.message(xenotest,"hello world");
    //		Thread.sleep(2000);
    //		freenode.quit("sayonara");
    //		println("FOO");
  }

}