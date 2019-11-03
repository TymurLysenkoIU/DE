package me.sitiritis.de.assignment.ui

import me.sitiritis.de.assignment.de_numerical_methods._
import me.sitiritis.de.assignment.plotly_ext
import plotly.element._
import plotly.{Plotly, Scatter, Trace}
import scala.collection.immutable.NumericRange
import cats.implicits._

object Plotting {
  case class DataForSolutionErrorPlot(solutionPlot: Scatter, errorPlot: Scatter)

  trait Plottable {
    def plotData(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DataForSolutionErrorPlot]
    val plotColor: Color.RGB
  }

  object PlottableEulerMethodForTask extends EulerMethodForTask with Plottable {
    override val plotColor: Color.RGB = Color.RGB(153, 0, 153)

    override def plotData(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DataForSolutionErrorPlot] = {
      solve(xs, initialY) map { s =>
        val xsd = xs map { _.toDouble }

        DataForSolutionErrorPlot(
          solutionPlot = Scatter(
            values = xsd,
            secondValues = s.ys map { _.toDouble },
            name = "Euler method",
            mode = ScatterMode(ScatterMode.Lines),
            line = Line(
              shape = LineShape.Spline,
              color = plotColor,
              dash = Dash.Dot,
              width = 3.0,
            ),
            hoverinfo = HoverInfo(
              HoverInfo.Name,
              HoverInfo.X,
              HoverInfo.Y,
            ),
            hoveron = HoverOn.Points
          ),
          errorPlot = Scatter(
            values = xsd,
            secondValues = s.le map { _.toDouble },
            name = "Euler method",
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
            hoveron = HoverOn.Points
          )
        )
      }
    }
  }

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

  object PlottableRungeKuttaMethodForTask extends RungeKuttaMethodForTask with Plottable {
    override val plotColor: Color.RGB = Color.RGB(255, 128, 0)

    override def plotData(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DataForSolutionErrorPlot] = {
      solve(xs, initialY) map { s =>
        val xsd = xs map { _.toDouble }

        DataForSolutionErrorPlot(
          solutionPlot = Scatter(
            values = xsd,
            secondValues = s.ys map { _.toDouble },
            name = "Runge-Kutta method",
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
            values = xsd,
            secondValues = s.le map { _.toDouble },
            name = "Runge-Kutta method",
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

  val solutionDivID = "solutionPlot"
  val localErrorDivID = "localErrorPlot"
  val maxLocalErrorDivID = "maxLocalErrorPlot"
  def plotSolution(data: Seq[Trace]): Unit = Plotly.plot(solutionDivID, data)
  def plotSolution(data: Trace): Unit = Plotly.plot(solutionDivID, data)
  def purgeSolution(): Unit = plotly_ext.Plotly.purge(solutionDivID)
  def plotLocalError(data: Seq[Trace]): Unit = Plotly.plot(localErrorDivID, data)
  def purgeLocalError(): Unit = plotly_ext.Plotly.purge(localErrorDivID)
  def plotMaxLocalError(data: Seq[Trace]): Unit = Plotly.plot(maxLocalErrorDivID, data)
  def purgeMaxLocalError(): Unit = plotly_ext.Plotly.purge(maxLocalErrorDivID)

  def getDataForPlots(ix: Double, fx: Double, iy:Double, ni: Int): List[DataForSolutionErrorPlot] = {
    val xs = Range.BigDecimal.inclusive(
      ix,
      fx,
      calculateStep(ix, fx, ni)
    )

    val result: List[DataForSolutionErrorPlot] =
      (PlottableEulerMethodForTask.plotData(xs, iy) map { List(_) } getOrElse Nil) :::
      (PlottableImprovedEulerMethodForTask.plotData(xs, iy) map { List(_) } getOrElse Nil) :::
      (PlottableRungeKuttaMethodForTask.plotData(xs, iy) map { List(_) } getOrElse Nil) :::
      (PlottableExactSolution.plotData(xs, iy) map { List(_) } getOrElse Nil) :::
      Nil

    result
  }

  private def redrawSolution(data: List[Scatter]): Unit = {
    purgeSolution()
    plotSolution(data)
  }

  private def redrawLocalError(data: List[Scatter]): Unit = {
    purgeLocalError()
    plotLocalError(data)
  }

  // TODO: depend on data to draw
  private def redrawMaxLocalError(): Unit = {
    purgeMaxLocalError()
  }

  def redrawPlots(data: List[DataForSolutionErrorPlot]): Unit = {
    redrawSolution(data map { _.solutionPlot })
    redrawLocalError(data map { _.errorPlot })
    //    redrawMaxLocalError()
  }
}
