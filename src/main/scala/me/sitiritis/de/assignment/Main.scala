package me.sitiritis.de.assignment

import org.querki.jquery._

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
    $(() => setupUI())
  }

  def setupUI(): Unit = {
    $("body").append("<p>Hello, World!</p>")
    $("#click-me-button").click(() => addClickedMessage())
  }

  def addClickedMessage(): Unit = {
    $("body").append("<p>Button clicked</p>")
  }
}
