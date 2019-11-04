package me.sitiritis.de.assignment

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import io.udash.wrappers.jquery._
import me.sitiritis.de.assignment.de_numerical_methods.calculateStep
import org.scalajs.dom.document

object Main {
  def main(args: Array[String]): Unit = {
    val initialX: Var[Double] = Var(0.0)
    val finalX: Var[Double] = Var(20.0)
    val initialY: Var[Double] = Var(1.0)
    val numberOfIntervals: Var[Int] = Var(200)
    val beginErrorInterval: Var[Int] = Var(200)
    val endErrorInterval: Var[Int] = Var(210)

    jQ(() => {
      dom.render(
        document.getElementById("mainContent"),
        ui.mainContainer(initialX, finalX, initialY, numberOfIntervals, beginErrorInterval, endErrorInterval)
      )

      val xs = Binding { calculateStep(initialX.bind, finalX.bind, numberOfIntervals.bind) map { s =>
          Range.BigDecimal.inclusive(initialX.value, finalX.value, s)
        } getOrElse Range.BigDecimal.inclusive(1.0, 0.0, 1.0) // empty range
      }
      xs.watch()

      // Redraw plot every time IVP changes
      Binding {
        ui.Plotting.redrawSolutionErrorPlots(ui.Plotting.getSolutionErrorDataForPlots(xs.bind, initialY.bind), Nil)
      }.watch()

      ui.Plotting.redrawMaxLocalError(
        ui.Plotting.getMaxLocalErrorDataForPlot(
          initialX.value,
          finalX.value,
          initialY.value,
          beginErrorInterval.value,
          endErrorInterval.value
        )
      )
    })
  }
}
