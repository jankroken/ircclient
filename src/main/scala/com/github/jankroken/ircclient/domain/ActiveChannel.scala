package com.github.jankroken.ircclient.domain

abstract class ActiveChannel {
  val network: String
  val name: String
  val selected: Boolean
  val activity: Boolean
  val nameMentioned: Boolean
}