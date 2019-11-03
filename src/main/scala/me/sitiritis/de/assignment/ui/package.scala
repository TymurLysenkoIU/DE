package me.sitiritis.de.assignment

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import io.udash.wrappers.jquery.jQ
import me.sitiritis.de.assignment.de_numerical_methods.{DENumericalSolution, EulerMethodForTask, ImprovedEulerMethodForTask, RungeKuttaMethodForTask, calculateStep}
import org.scalajs.dom.html.Element
import plotly.element.{AxisReference, Color, Dash, HoverInfo, HoverOn, Line, LineShape, ScatterMode}
import plotly.{Plotly, Scatter, Trace}

// TODO: Validation
package object ui {
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

  case class DataForSolutionErrorPlot(solutionPlot: Scatter, errorPlot: Scatter)

  def getDataForPlots(ix: Double, fx: Double, iy:Double, ni: Int): List[DataForSolutionErrorPlot] = {
    val xs = Range.BigDecimal.inclusive(
      ix,
      fx,
      de_numerical_methods.calculateStep(ix, fx, ni)
    )
    val xsd = xs map { _.toDouble }

    val euler = () => EulerMethodForTask(xs, iy) map { es =>
      val eulerPlotColor = Color.RGB(153, 0, 153)

      DataForSolutionErrorPlot(
        solutionPlot = Scatter(
          values = xsd,
          secondValues = es.ys map { _.toDouble },
          name = "Euler method",
          mode = ScatterMode(ScatterMode.Lines),
          line = Line(
            shape = LineShape.Spline,
            color = eulerPlotColor,
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
          secondValues = es.le map { _.toDouble },
          name = "Euler method",
          mode = ScatterMode(ScatterMode.Lines),
          line = Line(
            shape = LineShape.Spline,
            color = eulerPlotColor,
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
    val improvedEuler = () => ImprovedEulerMethodForTask(xs, iy) map { es =>
      val improvedEulerPlotColor = Color.RGB(0, 220, 255)

      DataForSolutionErrorPlot(
        solutionPlot = Scatter(
          values = xsd,
          secondValues = es.ys map { _.toDouble },
          name = "Improved Euler method",
          mode = ScatterMode(ScatterMode.Lines),
          line = Line(
            shape = LineShape.Spline,
            color = improvedEulerPlotColor,
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
          secondValues = es.le map { _.toDouble },
          name = "Improved Euler method",
          mode = ScatterMode(ScatterMode.Lines),
          line = Line(
            shape = LineShape.Spline,
            color = improvedEulerPlotColor,
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
    val rungeKutta = () => RungeKuttaMethodForTask(xs, iy) map { es =>
      val rungeKuttaPlotColor = Color.RGB(255, 128, 0)

      DataForSolutionErrorPlot(
        solutionPlot = Scatter(
          values = xsd,
          secondValues = es.ys map { _.toDouble },
          name = "Runge-Kutta method",
          mode = ScatterMode(ScatterMode.Lines),
          line = Line(
            shape = LineShape.Spline,
            color = rungeKuttaPlotColor,
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
          secondValues = es.le map { _.toDouble },
          name = "Runge-Kutta method",
          mode = ScatterMode(ScatterMode.Lines),
          line = Line(
            shape = LineShape.Spline,
            color = rungeKuttaPlotColor,
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

    // TODO: boolean variables
    val result: List[DataForSolutionErrorPlot] =
      (if (true) euler() map { List(_) } getOrElse Nil else Nil) :::
      (if (true) improvedEuler() map { List(_) } getOrElse Nil else Nil) :::
      (if (true) rungeKutta() map { List(_) } getOrElse Nil else Nil) :::
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

  @dom def ivpInputForm(
    initialX: Var[Double],
    finalX: Var[Double],
    initialY: Var[Double],
    numberOfIntervals: Var[Int]
  ): Binding[Element] = {
    <div class="formPart" id="ivpInputForm">
      <p>
        y({ Inputs.doubleInlineInput(initialX, "initialXInput").bind }) =
        { Inputs.doubleInlineInput(initialY, "initialYInput").bind }
      </p>
      <p>
        x belongs to [
        { initialX.bind.toString };
        { Inputs.doubleInlineInput(finalX, "endXInput").bind }
        ];
      </p>
      <p>
        Number of intervals { Inputs.intInlineInput(numberOfIntervals, "numberOfIntervalsInput").bind }
        (step = { de_numerical_methods.calculateStep(initialX.bind, finalX.bind, numberOfIntervals.bind).toString })
      </p>
    </div>
  }

  @dom def errorInputForm(
    beginErrorInterval: Var[Int],
    endErrorInterval: Var[Int]
  ): Binding[Element] = {
    <div class="formPart" id="errorInputForm">
      <p>Number of intervals for the error:</p>
      <p>
        from
        { Inputs.intInlineInput(beginErrorInterval, "beginErrorIntervalInput").bind }
        to
        { Inputs.intInlineInput(endErrorInterval, "endErrorInterval").bind }
      </p>
    </div>
  }

  @dom def inputForm(
    initialX: Var[Double],
    finalX: Var[Double],
    initialY: Var[Double],
    numberOfIntervals: Var[Int],
    beginErrorInterval: Var[Int],
    endErrorInterval: Var[Int]
  ): Binding[Element] = {
    <div class="inputForm">
      { ivpInputForm(initialX, finalX, initialY, numberOfIntervals).bind }
      { errorInputForm(beginErrorInterval, endErrorInterval).bind }
    </div>
  }

  @dom def mainContainer(
    initialX: Var[Double],
    finalX: Var[Double],
    initialY: Var[Double],
    numberOfIntervals: Var[Int],
    beginErrorInterval: Var[Int],
    endErrorInterval: Var[Int]
  ): Binding[Element] = {
    <div class="mainContainer">
      { inputForm(initialX, finalX, initialY, numberOfIntervals, beginErrorInterval, endErrorInterval).bind }
      <div id="mainPlot">
        <div id="solutionPlot"></div>
      </div>
      <div id="errorPlots">
        <div id="localErrorPlot"></div>
        <div id="maxLocalErrorPlot"></div>
      </div>
    </div>
  }
}
