package me.sitiritis.de.assignment

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.dom
import io.udash.wrappers.jquery._
import me.sitiritis.de.assignment.de_numerical_methods.{EulerMethod, EulerMethodForTask}
import org.scalajs.dom.document
import plotly._

object Main {
  def main(args: Array[String]): Unit = {
    val trace1 = Scatter(
      Seq(1, 2, 3, 4),
      Seq(10, 15, 13, 17)
    )

    val trace2 = Scatter(
      Seq(1, 2, 3, 4),
      Seq(16, 5, 11, 9)
    )

    val data = Seq(trace1, trace2)

    val initialX: Var[Double] = Var(0.0)
    val endX: Var[Double] = Var(20.0)
    val initialY: Var[Double] = Var(1.0)
    val numberOfIntervals: Var[Int] = Var(200)
    val beginErrorInterval: Var[Int] = Var(1)
    val endErrorInterval: Var[Int] = Var(100)

    jQ(() => {
      dom.render(
        document.getElementById("mainContainer"),
        ui.mainContainer(
          initialX,
          initialY,
          endX,
          numberOfIntervals,
          beginErrorInterval,
          endErrorInterval
        )
      )

      val xs = Range.BigDecimal.inclusive(initialX.value, endX.value, (endX.value - initialX.value) / numberOfIntervals.value)

      EulerMethodForTask(xs, initialY.value) foreach { ys =>
        val xl = xs.toList map { _.toDouble }
        println((xl, ys))
        ui.plotSolution(Seq(Scatter(xl, ys)))
      }
      ui.plotError(data)
    })
  }
}
