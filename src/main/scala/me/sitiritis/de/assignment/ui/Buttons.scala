package me.sitiritis.de.assignment.ui

import com.thoughtworks.binding.{Binding, dom}
import io.udash.wrappers.jquery.jQ
import me.sitiritis.de.assignment.de_numerical_methods.{EulerMethod, deFunction}
import org.scalajs.dom.raw.Event
import org.scalajs.dom.html.Element
import plotly.Scatter

object Buttons {
  private def redrawSolution(): Unit = {
    jQ("#solutionPlot").empty()
//    plotly.Plotly.purge

    EulerMethod(
      jQ("#initialXInput").value.asInstanceOf[String].toDouble,
      jQ("#endXInput").value.asInstanceOf[String].toDouble,
      jQ("#initialYInput").value.asInstanceOf[String].toDouble,
      jQ("#numberOfIntervalsInput").value.asInstanceOf[String].toInt
    ) { deFunction } foreach { r =>
      println(r)
      val (xs, ys) = r
      plotSolution(Scatter(xs, ys))
    }
    // TODO: call numerical methods
  }

  private def redrawError(): Unit = {
    jQ("#errorPlot").empty()
    // TODO: call numerical methods
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
