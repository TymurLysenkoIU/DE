package me.sitiritis.de.assignment

import scala.collection.immutable.NumericRange

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
