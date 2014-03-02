package com.github.jankroken.ircclient.actors

import com.github.jankroken.ircclient.protocol.domain.{UserMode, User}

object TestUser extends User {
  val xenobot7 = "xenobot7"
  val nick = xenobot7
  val realName = "αω" // "Jan Kroken"
  val userName = xenobot7
  val userMode = new UserMode()
}
