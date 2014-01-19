package com.github.jankroken.ircclient.protocol

import com.github.jankroken.ircclient.protocol.domain.{Channel, IRCServer, UserMode, User}

object QuakeNetListener extends App {
  testInitialContact

  def testInitialContact() {

    val xenobot1 = "xenobot1"
    object xeno extends User {
      val nick = xenobot1
      val realName = "Jan Kroken"
      val userName = xenobot1
      val userMode = new UserMode()
    }
    val quakenet = new IRCServer("irc.quakenet.org")
    quakenet.user = xeno
    quakenet.connect
    Thread.sleep(2000)
    quakenet.setNick(xenobot1)
    Thread.sleep(2000)
    quakenet.logon
    Thread.sleep(4000)
    var java: Channel = quakenet.join("#java")
    var norge: Channel = quakenet.join("#norge")
    Thread.sleep(2000000)
    //		freenode.message(xenotest,"hello world");
    //		Thread.sleep(2000);
    //		freenode.quit("sayonara");
    //		println("FOO");
  }

}