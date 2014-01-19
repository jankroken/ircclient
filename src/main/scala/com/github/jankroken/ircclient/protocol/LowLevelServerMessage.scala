package com.github.jankroken.ircclient.protocol

import com.github.jankroken.ircclient.protocol.domain._
import com.github.jankroken.ircclient.protocol.domain.NickAndUserAtHost
import com.github.jankroken.ircclient.protocol.domain.NickAtHost
import scala.Some

class LowLevelServerMessage(val origin: Option[Origin], val command: String, val arguments: Array[String]) extends ServerMessage {

  override def toString(): String = {
    val sb: StringBuilder = new StringBuilder(1024)

    origin match {
      case Some(origin) => sb.append('(').append(origin).append(") ")
      case None =>
    }

    sb.append(command)
    for (argument <- arguments) {
      sb.append(" \"")
      sb.append(argument)
      sb.append('"')
    }
    sb.toString()
  }

}

object LowLevelServerMessage {

  def apply(line: String): LowLevelServerMessage = {
    require(line != null)
    require(line.length > 2)

    case class SignedCommand(val origin: Option[Origin], val commandString: String)

    var workline = line

    val unparsedCommand =
      if (workline(0) == ':') {
        new SignedCommand(
          Some(sender(workline.substring(1, workline.indexOf(' ')))),
          workline.substring(workline.indexOf(' ') + 1))
      } else {
        new SignedCommand(None, workline)
      }
    if (unparsedCommand.commandString(0) == ' ') {
      println("ERROR: Command started with space")
    }
    val hasArguments = unparsedCommand.commandString.indexOf(' ') > 0
    val parsedCommand =
      if (hasArguments) {
        unparsedCommand.commandString.substring(0, unparsedCommand.commandString.indexOf(' '))
      } else {
        unparsedCommand.commandString
      }
    val arguments: List[String] =
      if (hasArguments) {
        val argumentString = unparsedCommand.commandString.substring(unparsedCommand.commandString.indexOf(' ') + 1)
        parseParams(argumentString)
      } else {
        List()
      }

    new LowLevelServerMessage(unparsedCommand.origin, parsedCommand, arguments.toArray[String])
  }


  def parseParams(line: String): List[String] = {
    def addFinalParam(argumentList: List[String], remainingInputLine: String): List[String] = {
      val finalArgument =
        if (remainingInputLine(0) == ':') {
          remainingInputLine.substring(1)
        } else {
          remainingInputLine
        }
      if (finalArgument.length > 0) {
        finalArgument :: argumentList
      } else {
        argumentList
      }
    }
    def recurse(result: List[String], restOfLine: String): List[String] = {
      if (restOfLine.isEmpty)
        result
      else if (restOfLine(0) == ':')
        restOfLine.substring(1) :: result
      else if (result.size == 13)
        addFinalParam(result, restOfLine)
      else {
        val nextSpace = restOfLine.indexOf(' ')
        if (nextSpace < 1)
          restOfLine :: result
        else {
          val firstArgument = restOfLine.substring(0, nextSpace)
          val restOfRestLine = restOfLine.substring(nextSpace + 1)
          recurse(firstArgument :: result, restOfRestLine)
        }
      }
    }
    recurse(Nil, line).reverse
  }

  def sender(senderString: String): Origin =
    senderString.split('@') match {
      case Array(nickAndPossiblyUser: String, domain: String) =>
        nickAndPossiblyUser.split('!') match {
          case Array(nick, user) => new NickAndUserAtHost(nick, user, domain)
          case _ => new NickAtHost(nickAndPossiblyUser, domain)
        }
      case _ => new Server(senderString)
    }
}
