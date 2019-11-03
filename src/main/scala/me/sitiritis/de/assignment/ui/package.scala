package me.sitiritis.de.assignment

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.html.Element

// TODO: Validation
package object ui {
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
      <header>
        <h1>Tymur Lysenko's [BS18-02] - Differential Equations Computational Practicum</h1>
      </header>
      { inputForm(initialX, finalX, initialY, numberOfIntervals, beginErrorInterval, endErrorInterval).bind }
      <div id="mainPlot">
        <div id={ Plotting.solutionDivID }></div>
      </div>
      <div id="errorPlots">
        <div id={ Plotting.localErrorDivID }></div>
        <div id={ Plotting.maxLocalErrorDivID }></div>
      </div>
    </div>
  }
}
