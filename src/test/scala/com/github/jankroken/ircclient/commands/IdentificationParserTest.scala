package com.github.jankroken.ircclient.commands

import org.scalatest._

class IdentificationParserTest extends FlatSpec with Matchers {

  "'/server irc.freenode.org'" should "result in ServerCommand('irc.freenode.net')" in {
    val ip = new IdentificationParser
    ip.parseCommand("/server irc.freenode.net") should equal (ip.ServerCommand("irc.freenode.net"))
  }

  "'/join #scala'" should "result in JoinCommand('#scala')" in {
    val ip = new IdentificationParser
    ip.parseCommand("/join #scala") should equal (ip.JoinCommand("#scala"))
  }

}
