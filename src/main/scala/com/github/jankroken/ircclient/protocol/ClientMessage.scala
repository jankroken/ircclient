package com.github.jankroken.ircclient.protocol

import com.github.jankroken.ircclient.protocol.domain.{Channel, User}

class ClientMessage(val commandString: String) {

}

object ClientMessage {

  def user(user: User): ClientMessage = {
    // 0 should be // user.getMode(),
    new ClientMessage(s"USER ${user.nick} 0 * ${user.realName}")
  }

  def pass(password: String): ClientMessage = {
    new ClientMessage(s"PASS $password")
  }

  def privmsg(channel: Channel, message: String): ClientMessage = {
    new ClientMessage(s"PRIVMSG $channel :$message")
  }

  def nick(name: String): ClientMessage = {
    new ClientMessage(s"NICK $name")
  }

  def join(channel: Channel): ClientMessage = {
    new ClientMessage(s"JOIN $channel")
  }

  def quit(message: String): ClientMessage = {
    new ClientMessage(s"QUIT :$message")
  }

  def pong(server: String): ClientMessage = {
    new ClientMessage(s"PONG $server")
  }

  def pong(server: String, server2: String): ClientMessage = {
    new ClientMessage(s"PONG $server $server2")
  }

}
