package me.sitiritis.de.assignment

import scala.collection.immutable.NumericRange
import me.sitiritis.de.assignment.de_numerical_methods.MathUtils.~=

package object de_numerical_methods {
  trait DENumericalMethod {
    def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution]
    def apply(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = solve(xs, initialY)
  }

  case class EulerMethod(
    f: (BigDecimal, BigDecimal) => Option[BigDecimal],
    e: (BigDecimal, BigDecimal) => Option[BigDecimal],
    c: (BigDecimal, BigDecimal) => Option[BigDecimal]
  ) extends DENumericalMethod {
    override def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = {
      def eulerMethodForValidData(r: NumericRange.Inclusive[BigDecimal], iy: BigDecimal) = {
        r.take(r.size - 1).foldLeft[Option[(List[BigDecimal], List[BigDecimal])]](Some((List(initialY), List(0.0)))) {
          (prev, x) =>
            val nextX = x + r.step
            for {
              p <- prev
              const <- c(xs.head, iy)
              exc <- e(nextX, const)
              fp <- f(x, p._1.head)
            } yield {
              val curY = p._1.head + (fp * r.step)
              val curLocalError = curY - exc
              (curY :: p._1, curLocalError :: p._2)
            }
        } map { res => DENumericalSolution(r, res._1.reverse, res._2.reverse) }
      }
      Some(eulerMethodForValidData(_, _)) filter { _ => xs.step < 1.0 } flatMap { _(xs, initialY) }
    }
  }

  class EulerMethodForTask extends EulerMethod(deFunction, exactSolutionFunction, exactSolutionConstant)
  object EulerMethodForTask extends EulerMethodForTask

  case class ImprovedEulerMethod(
    f: (BigDecimal, BigDecimal) => Option[BigDecimal],
    e: (BigDecimal, BigDecimal) => Option[BigDecimal],
    c: (BigDecimal, BigDecimal) => Option[BigDecimal]
  ) extends DENumericalMethod {
    override def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = {
      def improvedEulerMethodForValidData(r: NumericRange.Inclusive[BigDecimal], iy: BigDecimal) = {
        r.take(r.size - 1).foldLeft[Option[(List[BigDecimal], List[BigDecimal])]](Some((List(initialY), List(0.0)))) {
          (prev, x) =>
            val nextX = x + r.step
            for {
              p <- prev
              const <- c(xs.head, iy)
              exc <- e(nextX, const)
              k1 <- f(x, p._1.head)
              k2 <- f(x + r.step, p._1.head + (r.step * k1))
            } yield {
              val curY = p._1.head + (r.step / 2.0) * (k1 + k2)
              val curLocalError = curY - exc
              (curY :: p._1, curLocalError :: p._2)
            }
        } map { res => DENumericalSolution(r, res._1.reverse, res._2.reverse) }
      }
      Some(improvedEulerMethodForValidData(_, _)) filter { _ => xs.step < 1.0 } flatMap { _(xs, initialY) }
    }
  }

  class ImprovedEulerMethodForTask extends ImprovedEulerMethod(deFunction, exactSolutionFunction, exactSolutionConstant)
  object ImprovedEulerMethodForTask extends ImprovedEulerMethodForTask

  case class RungeKuttaMethod(
    f: (BigDecimal, BigDecimal) => Option[BigDecimal],
    e: (BigDecimal, BigDecimal) => Option[BigDecimal],
    c: (BigDecimal, BigDecimal) => Option[BigDecimal]
  ) extends DENumericalMethod {
    override def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = {
      def improvedEulerMethodForValidData(r: NumericRange.Inclusive[BigDecimal], iy: BigDecimal) = {
        r.take(r.size - 1).foldLeft[Option[(List[BigDecimal], List[BigDecimal])]](Some((List(initialY), List(0.0)))) {
          (prev, x) =>
            val nextX = x + r.step
            for {
              p <- prev
              const <- c(xs.head, iy)
              exc <- e(nextX, const)
              k1 <- f(x, p._1.head)
              k2 <- f(x + (r.step / 2.0), p._1.head + ((r.step / 2.0) * k1))
              k3 <- f(x + (r.step / 2.0), p._1.head + ((r.step / 2.0) * k2))
              k4 <- f(x + r.step, p._1.head + (r.step * k3))
            } yield {
              val curY = p._1.head + ((r.step / 6.0) * (k1 + (2.0 * k2) + (2.0 * k3) + k4))
              val curLocalError = curY - exc
              (curY :: p._1, curLocalError :: p._2)
            }
        } map { res => DENumericalSolution(r, res._1.reverse, res._2.reverse) }
      }
      Some(improvedEulerMethodForValidData(_, _)) filter { _ => xs.step < 1.0 } flatMap { _(xs, initialY) }
    }
  }

  class RungeKuttaMethodForTask extends RungeKuttaMethod(deFunction, exactSolutionFunction, exactSolutionConstant)
  object RungeKuttaMethodForTask extends RungeKuttaMethodForTask

  def calculateStep(ix: BigDecimal, fx: BigDecimal, n: Int): BigDecimal = (fx - ix) / n

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
