package io.mwielocha.wm4sq.ical

import biweekly.component.VEvent

trait EventEncoder[T] {
  def encode(e: T): VEvent
}
