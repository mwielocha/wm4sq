package io.mwielocha.wm4sq.model.forsquare


import biweekly.ICalendar
import io.circe.Decoder
import io.mwielocha.wm4sq.ical.CalendarEncoder
import io.mwielocha.wm4sq.ical.Implicits._

object Checkins {
  implicit val decode: Decoder[Checkins] = {
    cursor =>
      for {
        count <- cursor
          .downField("response")
          .downField("checkins")
          .downField("count")
          .as[Long]
        items <- cursor
          .downField("response")
          .downField("checkins")
          .downField("items")
          .as[Seq[Checkin]]
      } yield Checkins(count, items)
  }

  implicit val calendarEncoder: CalendarEncoder[Checkins] = {
    checkins =>
      val calendar = new ICalendar()
      checkins.items.foreach {
        item =>
          calendar.addEvent {
            item.asEvent
          }
      }

      calendar
  }
}

case class Checkins (
  count: Long,
  items: Seq[Checkin]
)
