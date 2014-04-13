package com.github.jankroken.ircclient.actors

import com.github.jankroken.ircclient.protocol.domain.{UserMode, User}

object TestUser extends User {
  val nick = "xenoTNG"
  val realName = "xeno" // "Jan Kroken"
  val userName = nick
  val userMode = new UserMode()
}
