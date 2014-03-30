package com.github.jankroken.ircclient.commands

import org.scalatest._
import com.github.jankroken.ircclient.commands.IdentificationParser.{HelpCommand, TextCommand, JoinCommand, ServerCommand}

class IdentificationParserTest extends FlatSpec with Matchers {

  "'/server irc.freenode.org'" should "result in ServerCommand('irc.freenode.net')" in {
    val ip = new IdentificationParser
    ip.parseCommand("/server irc.freenode.net") should equal (ServerCommand("irc.freenode.net"))
  }

  "'/join #scala'" should "result in JoinCommand('#scala')" in {
    val ip = new IdentificationParser
    ip.parseCommand("/join #scala") should equal (JoinCommand("#scala"))
  }

  "I am a happy little duckling" should "result in TextCommand('I am a happy little duckling')" in {
    val ip = new IdentificationParser
    ip.parseCommand("I am a happy little duckling") should equal (TextCommand("I am a happy little duckling"))
  }

  "/help" should "result in HelpCommand(None)" in {
    val ip = new IdentificationParser
    ip.parseCommand("/help") should equal (HelpCommand(None))
  }

  "/help topic" should "result in HelpCommand(Some(topic))" in {
    val ip = new IdentificationParser
    ip.parseCommand("/help topic") should equal (HelpCommand(Some("topic")))
  }

}
