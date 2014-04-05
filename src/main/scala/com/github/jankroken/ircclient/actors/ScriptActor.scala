package com.github.jankroken.ircclient.actors

import akka.actor.{ActorLogging, Actor}
import java.io.{File, StringReader, Reader}

class ScriptActor extends Actor with ActorLogging {

  val home = new File(System.getenv("HOME"))
  val ircFolder = new File(home,".glazed")

  def ensureFolderExist(folder:File):Boolean = {
    if (folder.isDirectory) true else folder.mkdir
  }

  def initFS {
    println(s"ensureExists($ircFolder)=>${ensureFolderExist(ircFolder)}")
    println(s"HOME=${home}")
    println(System.getenv())
  }

  new clojure.lang.RT // this might be once-per-vm, so should maybe be moved to the main method?

  def loadClojure(reader:Reader) = {
    clojure.lang.Compiler.load(reader)
  }
  def loadClojure(code:String) = {
    clojure.lang.Compiler.load(new StringReader(code))
  }

  def receive = {
    case "init" => initFS
    case "reload" => println(s"eval: ${loadClojure("(+ 1 2)")}")
    case _ => println("Hi, Script")
  }

}
