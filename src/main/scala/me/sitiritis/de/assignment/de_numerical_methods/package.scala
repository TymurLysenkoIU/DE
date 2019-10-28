package me.sitiritis.de.assignment

import scala.collection.immutable.NumericRange

package object de_numerical_methods {
  trait DENumericalMethod {
    def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: Double): Option[List[Double]]
    def apply(xs: NumericRange.Inclusive[BigDecimal], initialY: Double): Option[List[Double]] = solve(xs, initialY)
  }

  case class EulerMethod(
    f: (Double, Double) => Option[Double],
    e: Double => Option[Double]
  ) extends DENumericalMethod {
    override def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: Double): Option[List[Double]] = {
      def eulerMethodForValidData(r: NumericRange.Inclusive[BigDecimal], iy: Double): Option[List[Double]] = {
        r.take(r.size - 1).foldLeft[Option[List[Double]]](Some(List(iy))) { (ys, x) =>
          for {
            yss <- ys
            fp <- f(x.toDouble, yss.head)
          } yield (yss.head + fp * r.step.asInstanceOf[BigDecimal].toDouble) :: yss
        } map { _.reverse }
      }
      Some(eulerMethodForValidData(_, _)) filter { _ => xs.step.asInstanceOf[BigDecimal].toDouble < 1.0 } flatMap { _(xs, initialY) }
    }
  }

  object EulerMethodForTask extends EulerMethod(deFunction, exactSolutionFunction)

  case class ImprovedEulerMethod(
    f: (Double, Double) => Option[Double],
    e: Double => Option[Double]
  ) extends DENumericalMethod {
    override def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: Double): Option[List[Double]] = {
      ???
    }
  }

  object ImprovedEulerMethodForTask extends ImprovedEulerMethod(deFunction, exactSolutionFunction)

  // TODO: more numerical methods

  def deFunction(x: Double, y: Double): Option[Double] = Some(5 - Math.pow(x, 2) - Math.pow(y, 2) + 2 * x * y)

  def exactSolutionFunction(x: Double): Option[Double] = ???
}