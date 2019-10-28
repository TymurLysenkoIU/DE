package me.sitiritis.de.assignment

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

package object plotly_ext {
  @js.native
  @JSGlobal
  object Plotly extends js.Object {
    def purge(div: String): Unit = js.native
  }
}
