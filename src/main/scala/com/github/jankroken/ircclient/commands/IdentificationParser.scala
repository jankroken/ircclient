package com.github.jankroken.ircclient.commands

import org.parboiled.scala._
import org.parboiled.errors.{ErrorUtils, ParsingException}
import com.github.jankroken.ircclient.commands.IdentificationParser._
import scala.Some

class IdentificationParser extends Parser {

  def command: Rule1[ASTNode] = rule { join | server | text | ctcpAction | help }
  def server: Rule1[ASTNode] = "/server" ~ whiteSpaceSeparator ~ serverName ~> ServerCommand
  def join: Rule1[ASTNode] = "/join" ~ whiteSpaceSeparator ~ channel ~> IdentificationParser.JoinCommand
  def ctcpAction : Rule1[ASTNode] = "/me" ~ whiteSpaceSeparator  ~ zeroOrMore(ANY) ~> IdentificationParser.CTCPActionCommand
  def text: Rule1[ASTNode] = !("/") ~ zeroOrMore(ANY) ~> IdentificationParser.TextCommand
  def help :Rule1[ASTNode] = { topicHelp | globalHelp }
  def topicHelp = "/help" ~ whiteSpaceSeparator ~ topic ~> ((s:String) ⇒ IdentificationParser.HelpCommand(Some(s)))
  def globalHelp = "/help" ~> ((s) ⇒ IdentificationParser.HelpCommand(None))

  def whiteSpace: Rule0 = rule { zeroOrMore(anyOf(" \n\r\t\f")) }
  def whiteSpaceSeparator = rule { oneOrMore(anyOf(" \n\r\t\f")) }
  def channel = "#" ~ oneOrMore(channelChar)
  def channelChar = { "A" - "Z" | "a" - "z" | "0" - "9" | "#" }
  def serverName = oneOrMore(serverChar)
  def serverChar = rule { "0" - "9" | "a" - "z" | "A" - "Z" | "." }
  def topic = oneOrMore(topicChar)
  def topicChar = { "A" - "Z" | "a" - "z" | "0" - "9" }
//  def anything = rule { zeroOrMore(EscapedChar | NormalChar)  }

  def parseCommand(input: String): ASTNode = {
    val parsingResult = ReportingParseRunner(command).run(input)
    parsingResult.result match {
      case Some(astRoot) ⇒ astRoot
      case None ⇒ throw new ParsingException("Invalid Command:\n" +
        ErrorUtils.printParseErrors(parsingResult))
    }
  }
}

object IdentificationParser {
  sealed abstract class ASTNode
  abstract class Command extends ASTNode
  case class ServerCommand(server:String) extends Command
  case class JoinCommand(channel:String) extends Command
  case class TextCommand(server:String) extends Command
  case class HelpCommand(topic:Option[String]) extends Command
  case class CTCPActionCommand(string:String) extends Command

}
