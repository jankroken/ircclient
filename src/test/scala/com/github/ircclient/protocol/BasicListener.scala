package com.googlecode.estuary.sirc

import com.github.jankroken.ircclient.protocol.domain.{Channel, User, UserMode, IRCServer}

object BasicListener extends App {
//  testInitialContact;

  override def main(args: Array[String]) {
    testInitialContact()
  }

  def testInitialContact() {

    val xenobot7 = "xenobot7"
    object xeno extends User {
      val nick = xenobot7
      val realName = "Jan Kroken"
      val userName = xenobot7
      val userMode = new UserMode()
    }
    val freenode = new IRCServer("irc.freenode.org")
    freenode.user = xeno
    freenode.connect
    Thread.sleep(2000)
    freenode.setNick(xenobot7)
    //		freenode.message(xenotest,d.sleep(2000)
    freenode.logon
    Thread.sleep(4000)
    var fealdia: Channel = freenode.join("#fealdia")
    var digitalgunfire: Channel = freenode.join("#digitalgunfire")
    var scala: Channel = freenode.join("#scala")

    Thread.sleep(2000000)
    //		Thread.sleep(2000)
    //		freenode.quit("sayonara")
    //		println("FOO")
  }

}