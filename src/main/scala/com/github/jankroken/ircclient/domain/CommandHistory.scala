package com.github.jankroken.ircclient.domain

class CommandHistory {
  private var history:List[String] = List()
  private var before:List[String] = List()
  private var after:List[String] = List()

  def last:Option[String] = history.headOption
  def jumpBack:Option[String] = {
    val retValue = before.headOption
    before match {
      case head::tail =>
        before = tail
        after = head::after
    }
    retValue
  }
  def jumpForward:Option[String] = {
    after match {
      case head :: tail =>
        val retval = head
        before = head :: before
        after = tail
        Some(retval)
      case _ =>
        before match {
          case head :: tail => Some(head)
          case _ => None
        }
    }
  }

  def add(line:String) {
    history = line :: history
    before = history
    after = List()
  }

}

