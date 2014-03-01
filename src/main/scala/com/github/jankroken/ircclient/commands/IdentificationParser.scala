//package com.github.jankroken.ircclient.commands
//
//import org.parboiled2.{Rule1, ParserInput, Parser}
//
//class IdentificationParser(val input:ParserInput) extends Parser {
//  def InputLine = rule { Expression }
//
//  def Expression: Rule1[IdentifiedCommand] = rule {
//      "/join" ~ Channel ~> new IdentifiedCommand.Join(_)
//  }
//
//  def Channel = "#[A-Za-z0-9_]".r
//
//}
