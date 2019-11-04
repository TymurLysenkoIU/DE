package me.sitiritis.de.assignment

import scala.collection.immutable.NumericRange

trait DENumericalMethod {

  def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution]

  def apply(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = solve(xs, initialY)
}
