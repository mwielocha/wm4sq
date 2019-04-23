package io.mwielocha.wm4sq.model.forsquare


import java.util.Date

import biweekly.component.VEvent
import biweekly.property.Geo
import io.circe.{Decoder, Encoder}
import io.circe.derivation.{deriveDecoder, deriveEncoder}
import io.mwielocha.wm4sq.ical._


object Checkin {

  implicit val encode: Encoder[Checkin] = deriveEncoder
  implicit val decode: Decoder[Checkin] = deriveDecoder

  implicit val eventEncoder: EventEncoder[Checkin] = {

    case Checkin(id, createdAt, _, Venue(_, name, location)) =>

      val event = new VEvent()
      event.setUid(id)
      val millis = createdAt * 1000
      event.setDateStart(new Date(millis))
      event.setDateEnd(new Date(millis + (60 * 1000)))
      event.setGeo(new Geo(location.lat, location.lng))
      event.setSummary(name)
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

case class Checkin(
  id: String,
  createdAt: Long,
  timeZoneOffset: Long,
  venue: Venue
)