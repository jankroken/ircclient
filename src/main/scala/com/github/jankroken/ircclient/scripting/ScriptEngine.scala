package com.github.jankroken.ircclient.scripting

import java.io.{StringReader, Reader}

class ScriptEngine {

  def load(reader:Reader) = {
    clojure.lang.Compiler.load(reader)
  }
  def load(code:String) = {
    clojure.lang.Compiler.load(new StringReader(code))
  }
}
