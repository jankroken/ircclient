package com.github.jankroken.ircclient.domain

case class CommandLine(val context:Option[Context],val line:String) {

}

object CommandLine {
  def apply(line:String):CommandLine = CommandLine(None,line)
//  def apply(context:Context,line:String) = CommandLine(Some(context),line)
}
