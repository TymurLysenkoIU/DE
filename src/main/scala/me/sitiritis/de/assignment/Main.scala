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

      Binding {
        val xs = Range.BigDecimal.inclusive(
          initialX.bind,
          finalX.bind,
          calculateStep(initialX.bind, finalX.bind, numberOfIntervals.bind)
        )

        ui.Plotting.redrawSolutionErrorPlots(ui.Plotting.getSolutionErrorDataForPlots(xs, initialY.bind), Nil)
      }.watch()

      Binding {
        ui.Plotting.redrawMaxLocalError(
          ui.Plotting.getMaxLocalErrorDataForPlot(
            initialX.bind,
            finalX.bind,
            initialY.bind,
            beginErrorInterval.bind,
            endErrorInterval.bind
          )
        )
      }.watch()
    })
  }
}
