package com.github.jankroken.ircclient.actors

import akka.actor.{ActorLogging, Actor}
import java.io.{InputStreamReader, File, StringReader, Reader}

class ScriptActor extends Actor with ActorLogging {
  class Callback {
    def sayHello { println("Hello, anyone?") }
    def sayHello(s:java.lang.String) { println(s"hello, $s")}
  }

  val home = new File(System.getenv("HOME"))
  val ircFolder = new File(home,".glazed")
  val initScript:Reader = try {
    val initScript = Thread.currentThread.getContextClassLoader.getResource("init.clj")
    println(s"Loading $initScript")
    new InputStreamReader(getClass.getClassLoader.getResourceAsStream("init.clj"))
  } catch {
    case t: Throwable =>
      println(s"error during init: $t")
      new StringReader("(println \"failed to load init script\")")
  }


  def ensureFolderExist(folder:File):Boolean = {
    if (folder.isDirectory) true else folder.mkdir
  }

  def initFS {
    println(s"ensureExists($ircFolder)=>${ensureFolderExist(ircFolder)}")
    println(s"HOME=${home}")
    println(System.getenv())
  }

  def runInitScript = {
    loadClojure(initScript)
    val init = clojure.lang.RT.`var`("ircclient","initiate-callbacks")
    init.invoke(new Callback)
  }

  new clojure.lang.RT // this might be once-per-vm, so should maybe be moved to the main method?

  def loadClojure(reader:Reader) = {
    clojure.lang.Compiler.load(reader)
  }
  def loadClojure(code:String) = {
    clojure.lang.Compiler.load(new StringReader(code))
  }

  def receive = {
    case "init" =>
      initFS
      runInitScript
    case "reload" =>
      println(s"eval: ${loadClojure("(+ 1 2)")}")
    case _ =>
      println("Hi, Script")
  }

}
