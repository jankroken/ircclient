package com.github.jankroken.ircclient.actors

import akka.actor.{ActorLogging, Actor}
import com.github.jankroken.ircclient.scripting.{ScriptEngine, ScriptEngineFactory}
import com.github.jankroken.ircclient.repository.FileSystemRepository
import com.github.jankroken.ircclient.commands.JoinCommand
import com.github.jankroken.ircclient.domain.ChannelTarget

class ScriptActor extends Actor with ActorLogging {
  class Callback {
    def sayHello { println("Hello, anyone?") }
    def sayHello(s:java.lang.String) { println(s"hello, $s")}
    def join(network:java.lang.String,channel:java.lang.String) = IRCActorSystem.main ! JoinCommand(ChannelTarget(network,channel))
  }

  def runInitScript(engine:ScriptEngine) = {
    FileSystemRepository.getReaderFromClassPath("init.clj") match {
      case Some(script) ⇒
        engine.load(script)
        val init = clojure.lang.RT.`var`("ircclient","initiate-callbacks")
        init.invoke(new Callback)
      case None ⇒
        println("Failed to load init script")
    }
  }

  def receive = {
    case "init" ⇒
      FileSystemRepository.initFS
      val engine = ScriptEngineFactory.getScriptEngine
      runInitScript(engine)
      context.become(initiated(engine))
    case _ ⇒ println("Not initiated")
  }

  def initiated(engine:ScriptEngine):Receive = {
    case "unload" ⇒
      println(s"eval: ${engine.load("(+ 1 2)")}")
      context.unbecome()
    case _ ⇒
      println("Hi, Script")
  }



}
