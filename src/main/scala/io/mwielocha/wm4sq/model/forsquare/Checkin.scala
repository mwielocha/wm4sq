package io.mwielocha.wm4sq.model.forsquare


import biweekly.component.VEvent
import biweekly.property.Geo
import io.circe.generic.JsonCodec
import io.mwielocha.wm4sq.ical._

import java.util.Date


object Checkin {

  implicit val eventEncoder: EventEncoder[Checkin] = {

    case Checkin(id, createdAt, _, Venue(_, name, location)) =>

      val event = new VEvent()
      event.setUid(id)
      val millis = createdAt * 1000
      event.setDateStart(new Date(millis))
      event.setDateEnd(new Date(millis + (60 * 1000)))
      event.setGeo(new Geo(location.lat, location.lng))
      event.setSummary(s"@$name")
      event.setLocation {
        Seq(
          location.address,
          location.city,
          location.postalCode,
          location.state,
          location.country,
        ).flatten.mkString(" ")
      }
      event
  }
}

@JsonCodec
case class Checkin(
  id: String,
  createdAt: Long,
  timeZoneOffset: Long,
  venue: Venue
)