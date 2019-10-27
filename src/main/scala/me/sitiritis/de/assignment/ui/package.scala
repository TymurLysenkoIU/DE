package me.sitiritis.de.assignment

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import org.scalajs.dom.html.Element

// TODO: Validation, button
package object ui {
  @dom def ivpInputForm(
    initialX: Var[Double],
    initialY: Var[Double],
    beginX: Var[Double],
    endX: Var[Double],
  ): Binding[Element] = {
    <div class="input-form">
      <p>
        y({ Inputs.doubleInlineInput(initialX, "initialXInput").bind }) =
        { Inputs.doubleInlineInput(initialY, "initialXInput").bind }
      </p>
      <p>
        x belongs to [
          { Inputs.doubleInlineInput(beginX, "beginXInput").bind };
          { Inputs.doubleInlineInput(endX, "endXInput").bind }
        ]
      </p>
    </div>
  }

  @dom def ivpPart(
    initialX: Var[Double],
    initialY: Var[Double],
    beginX: Var[Double],
    endX: Var[Double],
  ): Binding[Element] = {
    <div class="container-item">
      { ivpInputForm(initialX, initialY, beginX, endX).bind }
      <div id="solutionPlot"></div>
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
      { errorInputForm(beginErrorInterval, endErrorInterval).bind }
      <div id="errorPlot"></div>
    </div>
  }

  @dom def mainContainer(
    initialX: Var[Double],
    initialY: Var[Double],
    beginX: Var[Double],
    endX: Var[Double],
    beginErrorInterval: Var[Int],
    endErrorInterval: Var[Int]
  ): Binding[Element] = {
    <div class="container">
      { ivpPart(initialX, initialY, beginX, endX).bind }
      { errorPart(beginErrorInterval, endErrorInterval).bind }
    </div>
  }
}
