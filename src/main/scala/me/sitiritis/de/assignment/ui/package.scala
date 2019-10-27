package me.sitiritis.de.assignment

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.html.Element
import plotly.{Plotly, Trace}

// TODO: Validation, button
package object ui {
  def plotSolution(data: Seq[Trace]): Unit = Plotly.plot("solutionPlot", data)
  def plotSolution(data: Trace): Unit = Plotly.plot("solutionPlot", data)
  def plotError(data: Seq[Trace]): Unit = Plotly.plot("errorPlot", data)

  @dom def ivpInputForm(
    initialX: Var[Double],
    initialY: Var[Double],
    beginX: Binding[Double],
    endX: Var[Double],
    numberOfIntervals: Var[Int]
  ): Binding[Element] = {
    <div class="input-form">
      <p>
        y({ Inputs.doubleInlineInput(initialX, "initialXInput").bind }) =
        { Inputs.doubleInlineInput(initialY, "initialYInput").bind }
      </p>
      <p>
        x belongs to [
          { beginX.bind.toString };
          { Inputs.doubleInlineInput(endX, "endXInput").bind }
        ];
      </p>
      <p>
        Number of intervals { Inputs.intInlineInput(numberOfIntervals, "numberOfIntervalsInput").bind }
      </p>
    </div>
  }

  @dom def ivpPart(
    initialX: Var[Double],
    initialY: Var[Double],
    beginX: Binding[Double],
    endX: Var[Double],
    numberOfIntervals: Var[Int]
  ): Binding[Element] = {
    <div class="container-item">
      <div>{ ivpInputForm(initialX, initialY, beginX, endX, numberOfIntervals).bind }</div>
      <div>{ Buttons.redrawSolutionButton().bind }</div>
      <div class="plot" id="solutionPlot"></div>
    </div>
  }

  @dom def errorInputForm(
    beginErrorInterval: Var[Int],
    endErrorInterval: Var[Int]
  ): Binding[Element] = {
    <div class="input-form">
      <p>Number of intervals for the error:</p>
      <p>
        from
        { Inputs.intInlineInput(beginErrorInterval, "beginErrorIntervalInput").bind }
        to
        { Inputs.intInlineInput(endErrorInterval, "endErrorInterval").bind }
      </p>
    </div>
  }

  @dom def errorPart(
    beginErrorInterval: Var[Int],
    endErrorInterval: Var[Int]
  ): Binding[Element] = {
    <div class="container-item">
      <div>{ errorInputForm(beginErrorInterval, endErrorInterval).bind }</div>
      <div>{ Buttons.redrawErrorButton().bind }</div>
      <div class="plot" id="errorPlot"></div>
    </div>
  }

  @dom def mainContainer(
    initialX: Var[Double],
    initialY: Var[Double],
    endX: Var[Double],
    numberOfIntervals: Var[Int],
    beginErrorInterval: Var[Int],
    endErrorInterval: Var[Int]
  ): Binding[Element] = {
    <div class="container">
      { ivpPart(initialX, initialY, initialX, endX, numberOfIntervals).bind }
      { errorPart(beginErrorInterval, endErrorInterval).bind }
    </div>
  }
}
