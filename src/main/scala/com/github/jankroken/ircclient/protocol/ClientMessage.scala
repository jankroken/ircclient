package com.github.jankroken.ircclient.protocol

import com.github.jankroken.ircclient.protocol.domain.{Channel, User}


class ClientMessage(val commandString: String){
	
}

object ClientMessage {

    def user(user: User):ClientMessage = {
        val cs = "USER %s %d * %s" format(
					         user.nick,
							 0, // user.getMode(),
							 user.realName)
        new ClientMessage(cs)
    }

    def pass(password: String):ClientMessage = {
        new ClientMessage("PASS "+password)
    }
  
    def privmsg(channel: Channel, message: String):ClientMessage = {
        new ClientMessage("PRIVMSG "+channel+" :"+message)
    }
  
    def nick(name: String):ClientMessage = {
        new ClientMessage("NICK "+name)
    }
  
    def join(channel: Channel):ClientMessage = {
        new ClientMessage("JOIN "+channel)
    }
  
    def quit(message: String):ClientMessage = {
	    new ClientMessage("QUIT :"+message)
    }
    
    def pong(server: String):ClientMessage = {
    	new ClientMessage("PONG "+server)
    }
    def pong(server: String, server2: String):ClientMessage = {
    	new ClientMessage("PONG "+server+" "+server2)
    }
    
}
