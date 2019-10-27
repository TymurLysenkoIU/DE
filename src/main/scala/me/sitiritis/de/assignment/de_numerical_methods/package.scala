package me.sitiritis.de.assignment

import scalaz.Validation

package object de_numerical_methods {
  trait DENumericalMethod {
    def apply(
      initialX: Double,
      finalX: Double,
      initialY: Double,
      numberOfIntervals: Int
    )(f: (Double, Double) => Option[Double]): Option[(List[Double], List[Double])]
  }

  object EulerMethod extends DENumericalMethod {
    override def apply(
      initialX: Double,
      finalX: Double,
      initialY: Double,
      numberOfIntervals: Int
    )(f: (Double, Double) => Option[Double]): Option[(List[Double], List[Double])] = {
      def calculateStep(ix: Double, fx: Double, n: Int): Option[BigDecimal] = {
        if ((numberOfIntervals > 0) && (initialX < finalX)) Some((finalX - initialX) / numberOfIntervals) else None
      }

      Some((step: BigDecimal) =>
        Range.BigDecimal.inclusive(initialX, finalX, step)
        .foldLeft[Option[(List[Double], List[Double])]](Some((List(initialX), List(initialY)))) {
          (ls: Option[(List[Double], List[Double])], x: BigDecimal) =>
            for {
              (xs, ys) <- ls
              fp <- f(xs.head, ys.head)
            } yield (x.toDouble :: xs, (ys.head + (fp * step.toDouble)) :: ys)
        }
      ) flatMap { rf => calculateStep(initialX, initialY, numberOfIntervals) filter { _ < 1 } flatMap { s => rf(s) } }
    }
  }

  // TODO: more numerical methods

  def deFunction(x: Double, y: Double): Option[Double] = Some(5 - Math.pow(x, 2) - Math.pow(y, 2) + 2 * x * y)
}