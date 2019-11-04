package me.sitiritis.de.assignment

import plotly.element.Color

import scala.collection.immutable.NumericRange

trait Plottable {
  def plotData(xs: NumericRange.Inclusive[BigDecimal], initialY: BigDecimal): Option[DataForSolutionErrorPlot]

  val plotColor: Color.RGB
}
