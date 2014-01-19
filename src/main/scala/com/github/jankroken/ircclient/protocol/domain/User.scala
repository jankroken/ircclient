package com.github.jankroken.ircclient.protocol.domain

abstract class User {

	val nick: String
	val userName: String
	val realName: String
	val userMode: UserMode

}
