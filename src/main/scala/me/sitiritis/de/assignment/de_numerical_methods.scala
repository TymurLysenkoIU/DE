package me.sitiritis.de.assignment

import MathUtils._

object de_numerical_methods {
  def calculateStep(ix: BigDecimal, fx: BigDecimal, n: Int): Option[BigDecimal] =
    if (n > 0) Some((fx - ix) / n) else None

  def deFunction(x: BigDecimal, y: BigDecimal): Option[BigDecimal] = Some(5 - x.pow(2) - y.pow(2) + 2 * x * y)

  def exactSolutionConstant(ix: Double, iy: Double): Option[Double] = {
    if (~=(iy, ix + 2.0)) None else Some((4.0 + iy - 2.0 - ix) / (Math.exp(4.0 * ix) * (iy - 2.0 - ix)))
  }

  def exactSolutionFunction(x: Double, c: Double): Option[Double] = {
    if (~=(Math.exp(4.0 * x) * c, 1.0)) None else Some(2.0 + x + (4.0 / ((c * Math.pow(Math.E, 4.0 * x)) - 1.0)))
  }

  def exactSolutionConstant(ix: BigDecimal, iy: BigDecimal): Option[BigDecimal] =
    exactSolutionConstant(ix.toDouble, iy.toDouble) map { BigDecimal(_) }

  def exactSolutionFunction(x: BigDecimal, c: BigDecimal): Option[BigDecimal] =
    exactSolutionFunction(x.toDouble, c.toDouble) map { BigDecimal(_) }
}
