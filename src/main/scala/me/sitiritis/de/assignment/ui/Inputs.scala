package me.sitiritis.de.assignment.ui

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import io.udash.wrappers.jquery.jQ
import org.scalajs.dom.html.Element
import org.scalajs.dom.raw.Event

object Inputs {
  @dom def doubleInlineInput(rv: Var[Double], inputID: String): Binding[Element] = {
    <input
      id={ inputID } type="number"
      value={ rv.bind.toString }
      onchange={_: Event =>
        jQ("#" + inputID).attr("value") map { v => rv.value = v.toDouble }
      }
    />
  }

  @dom def intInlineInput(rv: Var[Int], inputID: String): Binding[Element] = {
    <input
      id={ inputID } type="number"
      value={ rv.bind.toString }
      onchange={ _: Event =>
        jQ("#" + inputID).attr("value") map { v => rv.value = v.toInt }
      }
    />
  }

//  @dom def inputInitialX(initialX: Var[Int], beginX: Binding[Int], endX: Binding[Int]): Binding[Element] = {
//    <input id="initialXInput" type="number"
//           value={ initialX.bind.toString }
//           onchange={ _: Event => initialX value_= initialXInput.value.toInt }
//    />
//  }
//
//  @dom def inputInitialY(initialY: Var[Int]): Binding[Element] = {
//    <input id="initialYInput" type="number"
//           value={ initialY.bind.toString }
//           onchange={ _: Event => initialY value_= initialYInput.value.toInt }
//    />
//  }
//
//  @dom def inputBeginX(beginX: Var[Int]): Binding[Element] = {
//    <input id="beginXInput" type="number"
//           value={ beginX.bind.toString }
//           onchange={ _: Event => beginX value_= beginXInput.value.toInt }
//    />
//  }
//
//  @dom def inputEndX(endX: Var[Int]): Binding[Element] = {
//    <input id="endXInput" type="number"
//           value={ endX.bind.toString }
//           onchange={ _: Event => endX value_= endXInput.value.toInt }
//    />
//  }
//
//  @dom def inputBeginErrorInterval(beginErrorInterval: Var[Int]): Binding[Element] = {
//    <input id="beginErrorIntervalInput" type="number"
//           value={ beginErrorInterval.bind.toString }
//           onchange={ _: Event => beginErrorInterval value_= beginErrorIntervalInput.value.toInt }
//    />
//  }
//
//  @dom def inputEndErrorInterval(endErrorInterval: Var[Int]): Binding[Element] = {
//      <input id="endErrorIntervalInput" type="number"
//             value={ endErrorInterval.bind.toString }
//             onchange={ _: Event => endErrorInterval value_= endErrorIntervalInput.value.toInt }
//      />
//  }
}
