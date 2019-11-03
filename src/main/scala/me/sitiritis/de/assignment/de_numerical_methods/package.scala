package me.sitiritis.de.assignment

import scala.collection.immutable.NumericRange

package object de_numerical_methods {
  trait DENumericalMethod {
    def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution]
    def apply(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = solve(xs, initialY)
  }

  case class EulerMethod(
    f: (BigDecimal, BigDecimal) => Option[BigDecimal],
    e: BigDecimal => Option[BigDecimal]
  ) extends DENumericalMethod {
    override def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = {
      def eulerMethodForValidData(r: NumericRange.Inclusive[BigDecimal], iy: BigDecimal) = {
        r.take(r.size - 1).foldLeft[Option[(List[BigDecimal], List[BigDecimal])]](Some((List(initialY), List(0.0)))) {
          (prev, x) =>
            val nextX = x + r.step

            for {
              p <- prev
              exc <- e(nextX)
              fp <- f(x, p._1.head)
            } yield {
              val curY = p._1.head + (fp * r.step)
              val curDiff = (curY - exc).abs
              val curLocalError = curDiff - p._2.head
              (curY :: p._1, curLocalError :: p._2)
            }
        } map { res => DENumericalSolution(r, res._1.reverse, res._2.reverse) }
      }
      Some(eulerMethodForValidData(_, _)) filter { _ => xs.step < 1.0 } flatMap { _(xs, initialY) }
    }
  }

  object EulerMethodForTask extends EulerMethod(deFunction, exactSolutionFunction)

  case class ImprovedEulerMethod(
    f: (BigDecimal, BigDecimal) => Option[BigDecimal],
    e: BigDecimal => Option[BigDecimal]
  ) extends DENumericalMethod {
    override def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = {
      def improvedEulerMethodForValidData(r: NumericRange.Inclusive[BigDecimal], iy: BigDecimal) = {
        r.take(r.size - 1).foldLeft[Option[(List[BigDecimal], List[BigDecimal])]](Some((List(initialY), List(0.0)))) {
          (prev, x) =>
            val nextX = x + r.step
            for {
              p <- prev
              exc <- e(nextX)
              k1 <- f(x, p._1.head)
              k2 <- f(x + r.step, p._1.head + (r.step * k1))
            } yield {
              val curY = p._1.head + (r.step / 2.0) * (k1 + k2)
              val curDiff = (curY - exc).abs
              val curLocalError = curDiff - p._2.head
              (curY :: p._1, curLocalError :: p._2)
            }
        } map { res => DENumericalSolution(r, res._1.reverse, res._2.reverse) }
      }
      Some(improvedEulerMethodForValidData(_, _)) filter { _ => xs.step < 1.0 } flatMap { _(xs, initialY) }
    }
  }

  object ImprovedEulerMethodForTask extends ImprovedEulerMethod(deFunction, exactSolutionFunction)

  case class RungeKuttaMethod(
    f: (BigDecimal, BigDecimal) => Option[BigDecimal],
    e: BigDecimal => Option[BigDecimal]
  ) extends DENumericalMethod {
    override def solve(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DENumericalSolution] = {
      def improvedEulerMethodForValidData(r: NumericRange.Inclusive[BigDecimal], iy: BigDecimal) = {
        r.take(r.size - 1).foldLeft[Option[(List[BigDecimal], List[BigDecimal])]](Some((List(initialY), List(0.0)))) {
          (prev, x) =>
            val nextX = x + r.step
            for {
              p <- prev
              exc <- e(nextX)
              k1 <- f(x, p._1.head)
              k2 <- f(x + (r.step / 2.0), p._1.head + ((r.step / 2.0) * k1))
              k3 <- f(x + (r.step / 2.0), p._1.head + ((r.step / 2.0) * k2))
              k4 <- f(x + r.step, p._1.head + (r.step * k3))
            } yield {
              val curY = p._1.head + ((r.step / 6.0) * (k1 + (2.0 * k2) + (2.0 * k3) + k4))
              val curDiff = (curY - exc).abs
              val curLocalError = curDiff - p._2.head
              (curY :: p._1, curLocalError :: p._2)
            }
        } map { res => DENumericalSolution(r, res._1.reverse, res._2.reverse) }
      }
      Some(improvedEulerMethodForValidData(_, _)) filter { _ => xs.step < 1.0 } flatMap { _(xs, initialY) }
    }
  }

  object RungeKuttaMethodForTask extends RungeKuttaMethod(deFunction, exactSolutionFunction)

  def calculateStep(ix: BigDecimal, fx: BigDecimal, n: Int): BigDecimal = (fx - ix) / n

  def deFunction(x: BigDecimal, y: BigDecimal): Option[BigDecimal] = Some(5 - x.pow(2) - y.pow(2) + 2 * x * y)

  def exactSolutionFunction(x: Double): Double = 2.0 + x - (4.0 / ((3 * Math.pow(Math.E, 4.0 * x)) + 1.0))
  def exactSolutionFunction(x: BigDecimal): Option[BigDecimal] = Some(exactSolutionFunction(x.toDouble))
}
