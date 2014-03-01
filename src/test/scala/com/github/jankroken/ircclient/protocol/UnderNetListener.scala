package com.github.jankroken.ircclient.protocol

import com.github.jankroken.ircclient.protocol.domain.{Channel, IRCServer, UserMode, User}

object UnderNetListener extends App {
  testInitialContact

  def testInitialContact() {

    val xenobot1 = "xenobot1"
    object xeno extends User {
      val nick = xenobot1
      val realName = "Jan Kroken"
      val userName = xenobot1
      val userMode = new UserMode()
    }
    val undernet = new IRCServer("irc.undernet.org")
    undernet.user = xeno
    undernet.connect
    Thread.sleep(2000)
    undernet.setNick(xenobot1)
    Thread.sleep(2000)
    undernet.logon
    Thread.sleep(4000)
    var java: Channel = undernet.join("#java")
    var norge: Channel = undernet.join("#norge")
    Thread.sleep(2000000)
    //		freenode.message(xenotest,"hello world");
    //		Thread.sleep(2000);
    //		freenode.quit("sayonara");
    //		println("FOO");
  }

}