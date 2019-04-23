package io.mwielocha.wm4sq.ical

import biweekly.ICalendar
import biweekly.component.VEvent

object Implicits {

  implicit class AsEvent[T](t: T) {
    def asEvent(implicit encoder: EventEncoder[T]): VEvent =
      encoder.encode(t)
  }

  implicit class AsCalendar[T](t: T) {
    def asCalendar(implicit encoder: CalendarEncoder[T]): ICalendar =
      encoder.encode(t)
  }

}
