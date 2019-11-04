package me.sitiritis.de.assignment

import plotly.Scatter
import plotly.element._

import scala.collection.immutable.NumericRange

object PlottableImprovedEulerMethodForTask extends ImprovedEulerMethodForTask with Plottable {
  override val plotColor: Color.RGB = Color.RGB(0, 220, 255)

  override def plotData(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DataForSolutionErrorPlot] = {
    solve(xs, initialY) map { s =>
      val xsd = xs map { _.toDouble }

      DataForSolutionErrorPlot(
        solutionPlot = Scatter(
          values = xsd,
          secondValues = s.ys map { _.toDouble },
          name = "Improved Euler method",
          mode = ScatterMode(ScatterMode.Lines),
          line = Line(
            shape = LineShape.Spline,
            color = plotColor,
            dash = Dash.DashDot,
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
          values = xsd,
          secondValues = s.le map { _.toDouble },
          name = "Improved Euler method",
          mode = ScatterMode(ScatterMode.Lines),
          line = Line(
            shape = LineShape.Spline,
            color = plotColor,
            dash = Dash.Solid,
          ),
          hoverinfo = HoverInfo(
            HoverInfo.Name,
            HoverInfo.X,
            HoverInfo.Y,
          ),
          hoveron = HoverOn.Points,
        )
      )
    }
  }
}
