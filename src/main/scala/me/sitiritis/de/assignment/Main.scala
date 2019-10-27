package me.sitiritis.de.assignment

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.dom
import io.udash.wrappers.jquery._
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
    val beginX: Var[Double] = Var(0.0)
    val endX: Var[Double] = Var(10.0)
    val initialY: Var[Double] = Var(1.0)
    val beginErrorInterval: Var[Int] = Var(0)
    val endErrorInterval: Var[Int] = Var(100)

    jQ(() => {
      dom.render(
        document.getElementById("mainContainer"),
        ui.mainContainer(
          initialX,
          beginX,
          endX,
          initialY,
          beginErrorInterval,
          endErrorInterval
        )
      )
      Plotly.plot("solutionPlot", data)
      Plotly.plot("errorPlot", data)
    })
  }
}
