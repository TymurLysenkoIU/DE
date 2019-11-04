package me.sitiritis.de.assignment

import me.sitiritis.de.assignment.de_numerical_methods.{exactSolutionConstant, exactSolutionFunction}
import plotly.Scatter
import plotly.element._
import cats.implicits._
import scala.collection.immutable.NumericRange

object PlottableExactSolution extends Plottable {
  val plotColor: Color.RGB = Color.RGB(0, 200, 0)

  def plotData(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DataForSolutionErrorPlot] = {
    val xsd = xs.toList map {
      _.toDouble
    }

    exactSolutionConstant(xsd.head, initialY) flatMap { c =>
      xsd traverse {
        exactSolutionFunction(_, c)
      } map { ys =>
        val ysd = ys map {
          _.toDouble
        }

        DataForSolutionErrorPlot(
          solutionPlot = Scatter(
            values = xsd,
            secondValues = ysd,
            name = "Exact solution",
            mode = ScatterMode(ScatterMode.Lines),
            line = Line(
              shape = LineShape.Spline,
              color = plotColor,
              dash = Dash.Solid,
              width = 1.0,
            ),
            hoverinfo = HoverInfo(
              HoverInfo.Name,
              HoverInfo.X,
              HoverInfo.Y,
            ),
            hoveron = HoverOn.Points,
          ),
          errorPlot = Scatter(
            values = List[Double](),
            secondValues = List[Double]()
          )
        )
      }
    }
  }
}
