package com.github.jankroken.ircclient.actors

import akka.actor.{ActorLogging, Actor}
import com.github.jankroken.ircclient.protocol.domain.{EndOfNames, Channel, NameList}
import com.github.jankroken.ircclient.domain.NicksForChannel

class NickAccumulatorActor extends Actor with ActorLogging {

  var nicksByChannel = Map[Channel,List[String]]()

  def receive = {
    case nickList:NameList ⇒ {
      val originalNicks:List[String] = if(nicksByChannel.contains(nickList.channel)) nicksByChannel(nickList.channel) else List()
      val nicks = originalNicks ::: nickList.nicklist.toList

      nicksByChannel = nicksByChannel + (nickList.channel -> nicks)
    }
    case endList:EndOfNames ⇒ {
      val channel = endList.channel
      val nicks = if (nicksByChannel.contains(channel)) nicksByChannel(channel) else List()
      val nicksForChannel = NicksForChannel(channel,nicks)
      nicksByChannel = nicksByChannel - channel
      println(s":::NL::: $channel $nicks $sender")
      sender ! nicksForChannel
    }
  }
}
