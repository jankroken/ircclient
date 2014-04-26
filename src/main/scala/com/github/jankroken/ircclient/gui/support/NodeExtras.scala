package com.github.jankroken.ircclient.gui.support

import scala.xml.{Atom, Comment, Text, Elem,Node}

class NodeExtras(n: Node) {
  def toJdkNode(doc: org.w3c.dom.Document): org.w3c.dom.Node =
    n match {
      case Elem(prefix, label, attributes, scope, children @ _*) =>
        // XXX: ns
        val r = doc.createElement(label)
        for (a <- attributes) {
          r.setAttribute(a.key, a.value.text)
        }
        for (c <- children) c match {
          case e:Elem => r.appendChild(NodeExtras(e).toJdkNode(doc))
          case n:Node => r.appendChild(NodeExtras(n).toJdkNode(doc))
          case _ => println(c)
        }
        r
      case Text(text) => doc.createTextNode(text)
      case Comment(comment) => doc.createComment(comment)
      case a: Atom[_] => doc.createTextNode(a.data.toString)
  }
}

object NodeExtras {
  def apply(n:Node) = new NodeExtras(n)
  def apply(e:Elem) = new ElemExtras(e)
}

class ElemExtras(e: Elem) extends NodeExtras(e) {
  override def toJdkNode(doc: org.w3c.dom.Document) =
    super.toJdkNode(doc).asInstanceOf[org.w3c.dom.Element]

  def toJdkDoc(doc:org.w3c.dom.Document) = {
    doc.appendChild(toJdkNode(doc))
    doc
  }
}
