package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.EventListener
import javafx.scene.control.{Label, ScrollPane}
import javafx.scene.layout.{VBox}
import javafx.scene.web.WebView
import javafx.beans.value.{ObservableValue, ChangeListener}
import org.w3c.dom.{Element, Node, Document}
import scala.collection.immutable.Queue
import com.github.jankroken.ircclient.gui.support.{ElemExtras, NodeExtras}

class HTMLChatPane(eventListener: EventListener) extends ScrollPane {

  var bufferedLines:Queue[Node] = Queue()

  val webView = new WebView
  val engine = webView.getEngine
  engine.documentProperty().addListener(
    new ChangeListener[Document] {
      override def changed(x:ObservableValue[_ <: Document],oldDoc:Document,newDoc:Document) {
        println(s"documentProperty:listener: $x $oldDoc $newDoc")
      }
    })
  engine.loadContent("<body id='body'><table id='content'></table></body>")

  setFitToWidth(true)
  setFitToHeight(true)
  setContent(webView)
  setManaged(true)
  setMaxHeight(10000)
  setMaxWidth(10000)
  setMinWidth(100)

  def sendTextInfoBlock(title:String,message:String) {
    val text = new VBox
    text.getStyleClass.add("infomessage")
    val titleLabel = new Label {
      setText(title)
      getStyleClass.add("infomessageHeader")
    }
    text.getChildren.add(titleLabel)
    message.split("\n").map(t ⇒ new Label { setText(t);setWrapText(true); getStyleClass.add("infomessageLine") }).foreach(text.getChildren.add)

    Thread.sleep(100)

    val document = engine.getDocument
    println(s"Document: $document")
    if (document != null) {
      try {
        val h1 = document.createElement("tr")
        val td = document.createElement("td")
        td.setAttribute("colspan","3")
        h1.appendChild(td)
        val label = document.createElement("div")
        val text = document.createElement("div")
        td.appendChild(label)
        td.appendChild(text)
        document.getElementById("content").appendChild(h1)
      } catch {
        case t:Throwable => println(t)
      }
    } else {
      println("DOM is null")
    }
  }

  def scrollToBottom = setVvalue(getVmax)

  def sendSimpleMessage(from:String,message:String) {
    val document = engine.getDocument
    if (document != null) {
      if (message == "!!printsource") {
        println(document)
      }
      val h1 = document.createElement("tr")
      val nickTD = document.createElement("td")
      nickTD.setTextContent(from)
      val messageTD = document.createElement("td")
      if (message == "!!html") {
        messageTD.appendChild(convertHTMLTree(document))
      } else {
        messageTD.setTextContent(message)
      }
      val timeTD = document.createElement("td")
      timeTD.setTextContent("HHMMSS")
      h1.appendChild(nickTD)
      h1.appendChild(messageTD)
      h1.appendChild(timeTD)
      document.getElementById("content").appendChild(h1)
    } else {
      println("DOM is null")
    }
    scrollToBottom
  }
  def convertHTMLTree(doc:Document) = {
    val xml = <h1>test</h1>
    new ElemExtras(xml).toJdkNode(doc)
  }
}


object HTMLChatPane {
  def apply(eventListener: EventListener) = new HTMLChatPane(eventListener)
}
