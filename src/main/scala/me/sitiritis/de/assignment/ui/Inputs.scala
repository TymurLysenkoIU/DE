package me.sitiritis.de.assignment.ui

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import io.udash.wrappers.jquery.jQ
import org.scalajs.dom.html.Element
import org.scalajs.dom.raw.Event

object Inputs {
  private def calculateInputWidth(contentLen: Int): Int = {
    (contentLen + 1) * 8
  }

  // TODO: input validation of characters
  @dom def doubleInlineInput(rv: Var[Double], inputID: String): Binding[Element] = {
    <input
      id={ inputID } type="text"
      value={ rv.bind.toString }
      onkeyup={ _: Event =>
        val self = jQ(s"#$inputID")
        self.width(calculateInputWidth(self.value.asInstanceOf[String].length))
      }
      onchange={ _: Event =>
        rv.value = jQ(s"#$inputID").value.asInstanceOf[String].toDouble
      }
    />
  }

  @dom def intInlineInput(rv: Var[Int], inputID: String): Binding[Element] = {
    <input
      id={ inputID } type="text"
      value={ rv.bind.toString }
      onkeyup={ _: Event =>
        val self = jQ(s"#$inputID")
        self.width(calculateInputWidth(self.value.asInstanceOf[String].length))
      }
      onchange={ _: Event =>
        jQ(s"#$inputID").attr("value") map { v => rv.value = v.toInt }
      }
    />
  }
}
