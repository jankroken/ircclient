package com.github.jankroken.ircclient.gui

import com.github.jankroken.ircclient.domain.EventListener
import javafx.scene.control.{Label, ScrollPane}
import javafx.scene.layout.{VBox}
import javafx.scene.web.WebView
import javafx.beans.value.{ObservableValue, ChangeListener}
import org.w3c.dom.{Document}
import scala.collection.immutable.Queue
import com.github.jankroken.ircclient.gui.support.{ElemExtras}
import scala.xml.Elem
import scala.io.Source

class HTMLChatPane(eventListener: EventListener) extends ScrollPane {

  var bufferedLines:Queue[Elem] = Queue()

  val webView = new WebView
  val engine = webView.getEngine
  engine.setJavaScriptEnabled(true)
  engine.documentProperty().addListener(
    new ChangeListener[Document] {
      override def changed(x:ObservableValue[_ <: Document],oldDoc:Document,newDoc:Document) {
        println(s"documentProperty:listener: $x $oldDoc $newDoc")
        if (newDoc != null) {
          val table = newDoc.getElementById("content")
          if (table != null) {
            bufferedLines.foreach {
              line =>
                table.appendChild(convertHTMLTree(newDoc, line))
            }
            bufferedLines = Queue()
          }
        }
      }
    })
//  val ircclient_css = this.getClass.getClassLoader.getResource("ircclient.css").toURI.toString
  val getResourceAsStream = this.getClass.getClassLoader.getResourceAsStream(_)
  val ircclient_css_as_text = Source.fromInputStream(getResourceAsStream("ircclient.css")).getLines.mkString("\n")
//  println(s"stylesheet = $ircclient_css")

  val initialHTMLContent =
    s"""<html>
      <head>
        <style id="ircclient_css">
          $ircclient_css_as_text
        </style>
      </head>
      <body id="body">
      <table id="content">
      <tr><td colspan="3"><a onclick="window.alert('test1');" href="">test</a></td></tr>
      <tr><td colspan="3"><a onclick="application.getHostServices().showDocument('http://www.vg.no');" href="">test</a></td></tr>
      </table>
      </body>
      </html>"""

  engine.loadContent(initialHTMLContent)

  println(initialHTMLContent)
//  println(    s"""<html>
//      <head>
//      <link rel="stylesheet" type="text/css" href="$$ircclient_css">
//      </head>
//      <body id="body">
//      <table id="content">
//      <tr><td colspan="3" onclick="getHostServices().showDocument('http://www.vg.no');">test</td></tr>
//      </table>
//      </body>
//      </html>""")


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
    try {
      val infoBox =
        <tr>
          <td colspan="3">
            <div class="textInfoLabel">{title}</div>
            <div class="textInfoText">{message}</div>
          </td>
        </tr>
      if (document != null) {
        document.getElementById("content").appendChild(convertHTMLTree(document, infoBox))
      } else {
        bufferedLines = bufferedLines.+:(infoBox)
      }
    } catch {
      case t:Throwable => println(t)
    }
  }

  def scrollToBottom = setVvalue(getVmax)

  def makeYoutubeLink(url:String) = {
    <tr>
      <td colspan="3">
        <iframe width="560" height="315" src="https://www.youtube.com/embed/cShlNVmW7SA" frameborder="0" allowfullscreen="true">
          youtube video here
        </iframe>
      </td>
    </tr>
  }

  def extractYoutubeLinks(message:String) = {
    val YoutubeLink = """https?://www.youtube.com/[a-z]*(?:/|\?v=)([\w-]*)""".r
    YoutubeLink.findAllIn(message).flatMap(text => YoutubeLink.unapplySeq(text).get).toList
  }

  def sendSimpleMessage(from:String,message:String) {
    val document = engine.getDocument
      val nickMessageTime =
        <tr>
          <td class="regularNick">{from}</td>
          <td class="regularMessage">{message}</td>
          <td class="timestamp">TIME</td>
        </tr>
      val youtubeLinks = extractYoutubeLinks(message).map(videoId =>
          <tr>
            <td colspan="3">
            <iframe width="560" height="315" src={s"https://www.youtube.com/embed/$videoId"} frameborder="0" allowfullscreen="true">
              youtube video here
            </iframe>
            </td>
          </tr>)
    if (document != null) {
      document.getElementById("content").appendChild(convertHTMLTree(document,nickMessageTime))
      println(youtubeLinks)
      youtubeLinks.foreach {
        link =>
          document.getElementById("content").appendChild(convertHTMLTree(document, link))
      }
    } else {
      bufferedLines = bufferedLines.+:(nickMessageTime)
      youtubeLinks.foreach { link =>
        bufferedLines = bufferedLines.+:(link)
      }

    }
    scrollToBottom
  }
  def convertHTMLTree(doc:Document,xml:Elem) = {
    new ElemExtras(xml).toJdkNode(doc)
  }
}


object HTMLChatPane {
  def apply(eventListener: EventListener) = new HTMLChatPane(eventListener)
}
