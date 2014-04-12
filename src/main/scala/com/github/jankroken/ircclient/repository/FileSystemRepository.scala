package com.github.jankroken.ircclient.repository

import java.io.{File, InputStreamReader, Reader}

object FileSystemRepository {

  val home = new File(System.getenv("HOME"))
  val ircFolder = new File(home,".glazed")

  def ensureFolderExist(folder:File):Boolean = {
    if (folder.isDirectory) true else folder.mkdir
  }

  def initFS {
    println(s"ensureExists($ircFolder)=>${ensureFolderExist(ircFolder)}")
  }

  def getReaderFromClassPath(file:String):Option[Reader] = try {
    val initScript = Thread.currentThread.getContextClassLoader.getResource(file)
    println(s"Loading $initScript")
    Some(new InputStreamReader(getClass.getClassLoader.getResourceAsStream(file)))
  } catch {
    case t: Throwable =>
      println(s"error during init: $t")
      None
  }

}
