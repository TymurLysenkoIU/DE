package me.sitiritis.de.assignment

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.{Binding, dom}
import io.udash.wrappers.jquery._
import org.scalajs.dom.document

object Main {
  def main(args: Array[String]): Unit = {
    val initialX: Var[Double] = Var(0.0)
    val finalX: Var[Double] = Var(20.0)
    val initialY: Var[Double] = Var(1.0)
    val numberOfIntervals: Var[Int] = Var(200)
    val beginErrorInterval: Var[Int] = Var(1)
    val endErrorInterval: Var[Int] = Var(100)

    jQ(() => {
      dom.render(
        document.getElementById("mainContent"),
        ui.mainContainer(initialX, finalX, initialY, numberOfIntervals, beginErrorInterval, endErrorInterval)
      )

      val redraw = Binding {
        ui.Plotting.redrawPlots(ui.Plotting.getDataForPlots(initialX.bind, finalX.bind, initialY.bind, numberOfIntervals.bind))
      }

      redraw.watch()
    })
  }
}
