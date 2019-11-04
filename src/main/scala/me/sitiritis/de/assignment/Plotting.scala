package me.sitiritis.de.assignment

import me.sitiritis.de.assignment.de_numerical_methods.calculateStep
import plotly.element._
import plotly.{Plotly, Scatter, Trace}
import cats.implicits._

import scala.collection.immutable.NumericRange

object Plotting {
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

  def getSolutionErrorDataForPlots(xs: NumericRange.Inclusive[BigDecimal], iy:Double): List[DataForSolutionErrorPlot] = {
    (PlottableEulerMethodForTask.plotData(xs, iy) map { List(_) } getOrElse Nil) :::
    (PlottableImprovedEulerMethodForTask.plotData(xs, iy) map { List(_) } getOrElse Nil) :::
    (PlottableRungeKuttaMethodForTask.plotData(xs, iy) map { List(_) } getOrElse Nil) :::
    (PlottableExactSolution.plotData(xs, iy) map { List(_) } getOrElse Nil) :::
    Nil
  }

  def getMaxLocalErrorDataForPlot(ix: Double, fx: Double, iy:Double, in: Int, fn: Int): List[Scatter] = {
    implicit object AbsoluteBigDecimalOrder extends cats.kernel.Order[BigDecimal] {
      override def compare(x: BigDecimal, y: BigDecimal): Int = x.abs.compareTo(y.abs)
    }

    val ns = Range.Int.inclusive(in, fn, 1)

    val (eys, ieys, rkys) = ns map { n =>
      calculateStep(ix, fx, n) map { s =>
        val xs = Range.BigDecimal.inclusive(
          ix,
          fx,
          s
        )

        (
          EulerMethodForTaskObject(xs, iy) flatMap { _.le.maximumOption(AbsoluteBigDecimalOrder) map { _.toString } } getOrElse "",
          ImprovedEulerMethodForTaskObject(xs, iy) flatMap { _.le.maximumOption(AbsoluteBigDecimalOrder) map { _.toString } } getOrElse "",
          RungeKuttaMethodForTaskObject(xs, iy) flatMap { _.le.maximumOption(AbsoluteBigDecimalOrder) map { _.toString } } getOrElse "",
        )
      } getOrElse ("", "", "")
    } unzip3

    List(
      Scatter(
        values = ns,
        secondValues = eys,
        name = "Euler method",
        mode = ScatterMode(ScatterMode.Lines, ScatterMode.Markers),
        line = Line(
          shape = LineShape.Spline,
          color = PlottableEulerMethodForTask.plotColor,
          dash = Dash.Solid,
          width = 3.0,
        ),
        hoverinfo = HoverInfo(
          HoverInfo.Name,
          HoverInfo.X,
          HoverInfo.Y,
        ),
        hoveron = HoverOn.Points,
        connectgaps = false
      ),

      Scatter(
        values = ns,
        secondValues = ieys,
        name = "Improved Euler method",
        mode = ScatterMode(ScatterMode.Lines, ScatterMode.Markers),
        line = Line(
          shape = LineShape.Spline,
          color = PlottableImprovedEulerMethodForTask.plotColor,
          dash = Dash.Solid,
          width = 3.0,
        ),
        hoverinfo = HoverInfo(
          HoverInfo.Name,
          HoverInfo.X,
          HoverInfo.Y,
        ),
        hoveron = HoverOn.Points,
        connectgaps = false
      ),

      Scatter(
        values = ns,
        secondValues = rkys,
        name = "Runge-Kutta method",
        mode = ScatterMode(ScatterMode.Lines, ScatterMode.Markers),
        line = Line(
          shape = LineShape.Spline,
          color = PlottableRungeKuttaMethodForTask.plotColor,
          dash = Dash.Solid,
          width = 3.0,
        ),
        hoverinfo = HoverInfo(
          HoverInfo.Name,
          HoverInfo.X,
          HoverInfo.Y,
        ),
        hoveron = HoverOn.Points,
        connectgaps = false
      )
    )
  }

  private def redrawSolution(data: List[Scatter]): Unit = {
    purgeSolution()
    plotSolution(data)
  }

  private def redrawLocalError(data: List[Scatter]): Unit = {
    purgeLocalError()
    plotLocalError(data)
  }

  def redrawSolutionErrorPlots(solutionErrorData: List[DataForSolutionErrorPlot], maxErrorData: List[Scatter]): Unit = {
    redrawSolution(solutionErrorData map { _.solutionPlot })
    redrawLocalError(solutionErrorData map { _.errorPlot })
  }

  def redrawMaxLocalError(data: List[Scatter]): Unit = {
    purgeMaxLocalError()
    plotMaxLocalError(data)
  }
}
