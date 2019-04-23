package io.mwielocha.wm4sq.ical

import biweekly.ICalendar

trait CalendarEncoder[T] {
  def encode(t: T): ICalendar
}
