package me.sitiritis.de.assignment

import com.thoughtworks.binding.{Binding, dom}
import com.thoughtworks.binding.Binding.Var
import io.udash.wrappers.jquery.jQ
import org.scalajs.dom.Event
import org.scalajs.dom.html.Element

object Inputs {
  private def calculateInputWidth(contentLen: Int): Int = {
    (contentLen + 1) * 8
  }

  private val doubleRegex = """(-?\d+(\.\d+)?)""".r
  private val intRegex = """(-?\d+)""".r

  @dom def doubleInlineInput(rv: Var[Double], inputID: String): Binding[Element] = {
    <input
      id={ inputID } type="text"
      value={ rv.bind.toString }
      onkeyup={ _: Event =>
        val self = jQ(s"#$inputID")
        self.width(calculateInputWidth(self.value.asInstanceOf[String].length))
      }
      onchange={ _: Event =>
        val thisElement = jQ(s"#$inputID")

        thisElement.value.asInstanceOf[String] match {
          case doubleRegex(s, _*) => {
            thisElement.removeClass("inputError")
            rv.value = s.toDouble
          }
          case _ => thisElement.addClass("inputError")
        }
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
        val thisElement = jQ(s"#$inputID")

        thisElement.value.asInstanceOf[String] match {
          case intRegex(s) => {
            thisElement.removeClass("inputError")
            rv.value = s.toInt
          }
          case _ => thisElement.addClass("inputError")
        }
      }
    />
  }
}
