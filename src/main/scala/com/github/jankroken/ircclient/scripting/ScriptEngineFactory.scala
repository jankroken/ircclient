package com.github.jankroken.ircclient.scripting

import java.io.{InputStreamReader, Reader}

object ScriptEngineFactory {

  lazy val loadedRuntime = new clojure.lang.RT
  def loadRuntime = loadedRuntime


  def getScriptEngine = {
    loadRuntime
    new ScriptEngine
  }

}
