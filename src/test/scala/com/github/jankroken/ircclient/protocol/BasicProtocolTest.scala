package com.github.jankroken.ircclient.protocol

import org.scalatest.junit.AssertionsForJUnit
import com.github.jankroken.ircclient.protocol.domain.{Channel, User, IRCServer, UserMode}
import org.junit.Test

class BasicProtocolTest extends AssertionsForJUnit with App {

  override def main(args: Array[String]) {
    testInitialContact
  }

  @Test def testInitialContact() {

//    val xenobot5 = "xenobot5"
    val xenobot5 = "xenobot5"
    object xeno extends User {
      val nick = xenobot5
      val realName = "Jan Kroken"
      val userName = xenobot5
      val userMode = new UserMode()
    }
    val freenode = new IRCServer("irc.freenode.org")
    freenode.user = xeno
    freenode.connect
    Thread.sleep(2000)
    freenode.setNick(xenobot5)
    Thread.sleep(2000)
    freenode.logon
    Thread.sleep(4000)
    val xenotest: Channel = freenode.join("#xenotest")
    Thread.sleep(2000)
    freenode.message(xenotest, "hello world")
    Thread.sleep(2000)
    freenode.quit("sayonara")
    println("FOO")
  }

}