package me.sitiritis.de.assignment.ui

import com.thoughtworks.binding.{Binding, dom}
import io.udash.wrappers.jquery.jQ
import me.sitiritis.de.assignment.de_numerical_methods.EulerMethodForTask
import org.scalajs.dom.html.Element
import org.scalajs.dom.raw.Event
import plotly.Scatter

object Buttons {
  private def redrawSolution(): Unit = {
    purgeSolution()

    val ix = jQ("#initialXInput").value.asInstanceOf[String].toDouble
    val fx = jQ("#endXInput").value.asInstanceOf[String].toDouble
    val iy = jQ("#initialYInput").value.asInstanceOf[String].toDouble
    val ni = jQ("#numberOfIntervalsInput").value.asInstanceOf[String].toInt

    val xs = Range.BigDecimal.inclusive(ix, fx, (fx - ix) / ni)

    EulerMethodForTask(xs, iy) foreach { ys =>
      val xl = xs.toList map { _.toDouble }
      println((xl, ys))
      plotSolution(Seq(Scatter(xl, ys)))
    }
    // TODO: call numerical methods
  }

  private def redrawError(): Unit = {
    // TODO: call numerical methods
    purgeError()
  }

  // TODO: inject validation
  @dom def redrawSolutionButton(): Binding[Element] = {
    <button
      onclick={ _: Event =>
        redrawSolution()
        redrawError()
    }>
      Solve the initial value problem
    </button>
  }

  // TODO: inject validation
  @dom def redrawErrorButton(): Binding[Element] = {
    <button
      onclick={ _: Event =>
        redrawError()
    }>
      Recalculate error for the new range
    </button>
  }
}
