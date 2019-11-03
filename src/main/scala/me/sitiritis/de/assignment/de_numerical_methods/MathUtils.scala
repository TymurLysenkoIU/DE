package me.sitiritis.de.assignment.de_numerical_methods

object MathUtils {
  def ~=(a: Double, b: Double, precision: Double): Boolean = (a - b).abs < precision

  def ~=(a: Double, b: Double): Boolean = ~=(a, b, 0.0000000001)
}
