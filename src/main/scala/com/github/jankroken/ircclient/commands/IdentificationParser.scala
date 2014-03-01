package com.github.jankroken.ircclient.commands

import org.parboiled.scala._
import org.parboiled.errors.{ErrorUtils, ParsingException}

class IdentificationParser extends Parser {
  sealed abstract class ASTNode
  abstract class Command extends ASTNode
  case class ServerCommand(server:String) extends Command
  case class JoinCommand(server:String) extends Command

  def command: Rule1[ASTNode] = rule { join | server }
  def server: Rule1[ASTNode] = "/server" ~ whiteSpaceSeparator ~ serverName ~> ServerCommand
  def join: Rule1[ASTNode] = "/join" ~ whiteSpaceSeparator ~ channel ~> JoinCommand


  def whiteSpace: Rule0 = rule { zeroOrMore(anyOf(" \n\r\t\f")) }
  def whiteSpaceSeparator = rule { oneOrMore(anyOf(" \n\r\t\f")) }
  def channel = "#" ~ oneOrMore(channelChar)
  def channelChar = { "A" - "Z" | "a" - "z" | "0" - "9" }
  def serverName = oneOrMore(serverChar)
  def serverChar = rule { "0" - "9" | "a" - "z" | "A" - "Z" | "." }

  def parseCommand(input: String): ASTNode = {
    val parsingResult = ReportingParseRunner(command).run(input)
    parsingResult.result match {
      case Some(astRoot) => astRoot
      case None => throw new ParsingException("Invalid JSON source:\n" +
        ErrorUtils.printParseErrors(parsingResult))
    }
  }


}
