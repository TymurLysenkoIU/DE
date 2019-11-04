package me.sitiritis.de.assignment.ui

import com.thoughtworks.binding.{Binding, dom}
import io.udash.wrappers.jquery.jQ
import me.sitiritis.de.assignment.ui
import org.scalajs.dom.Event
import org.scalajs.dom.html.Element

object Buttons {
  @dom def redrawMaxLocalErrorButton(): Binding[Element] = {
    <button
      onmouseup={ _: Event =>
        ui.Plotting.redrawMaxLocalError(
          ui.Plotting.getMaxLocalErrorDataForPlot(
            jQ("#initialXInput").value().asInstanceOf[String].toDouble,
            jQ("#endXInput").value().asInstanceOf[String].toDouble,
            jQ("#initialYInput").value().asInstanceOf[String].toDouble,
            jQ("#beginErrorIntervalInput").value().asInstanceOf[String].toInt,
            jQ("#endErrorInterval").value().asInstanceOf[String].toInt,
          )
        )
      }
    >
    Recalculate max local errors for the range
    </button>
  }
}
