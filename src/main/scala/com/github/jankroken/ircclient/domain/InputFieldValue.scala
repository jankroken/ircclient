package com.github.jankroken.ircclient.domain

case class InputFieldValue(value:String, position:Int) {

  val ValidTailNick = "([a-zA-Z0-9\\\\\\{\\}\\[\\]\\|^`]*)$".r
  val beforeCaret = value.substring(0,position)

  val potentialNickStart = ValidTailNick.findFirstIn(beforeCaret).getOrElse("")
  def findLongestSafeNickMatch(nicks:List[String]):(String,List[String]) = {
    val matchingNicks = nicks.filter(_.startsWith(potentialNickStart))
    println(s"matches: $matchingNicks")
    (potentialNickStart,matchingNicks)
  }

}
