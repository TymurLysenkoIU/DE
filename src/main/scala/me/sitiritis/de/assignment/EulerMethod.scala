package me.sitiritis.de.assignment

import scala.collection.immutable.NumericRange

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
