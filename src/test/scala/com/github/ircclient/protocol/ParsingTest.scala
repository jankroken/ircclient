package com.googlecode.estuary.sirc

import com.googlecode.estuary.sirc.protocol.LowLevelServerMessage

object ParsingTest extends App {
  testParamParser

  def parseParams(paramLine: String): List[String] = {
    LowLevelServerMessage.parseParams(paramLine)
  }

  def testParamParser = {
    val p1 = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15"
    val e1 = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14 15")
    val p2 = "1 2 3 4 5 6 7 8 9 10 11 12 13 :14 15"
    val e2 = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14 15")
    val p3 = "1 2 :3 4 5 6"
    val e3 = List("1", "2", "3 4 5 6")
    val p4 = "1: 2 3 4"
    val e4 = List("1:", "2", "3", "4")
    println("p1: " + parseParams(p1))
    println("eq1:" + (parseParams(p1).equals(e1)))
    println("p2: " + parseParams(p2))
    println("eq2:" + (parseParams(p2).equals(e2)))
    println("p3: " + parseParams(p3))
    println("eq3:" + (parseParams(p3).equals(e3)))
    println("p4: " + parseParams(p4))
    println("eq4:" + (parseParams(p4).equals(e4)))
  }
}
