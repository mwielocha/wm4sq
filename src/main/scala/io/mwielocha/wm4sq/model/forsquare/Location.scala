package io.mwielocha.wm4sq.model.forsquare

import io.circe.{Decoder, Encoder}
import io.circe.derivation.{deriveDecoder, deriveEncoder}

object Location {
  implicit val encode: Encoder[Location] = deriveEncoder
  implicit val decode: Decoder[Location] = deriveDecoder
}

case class Location (
  lat: Double,
  lng: Double,
  address: Option[String],
  country: Option[String],
  postalCode: Option[String],
  cc: Option[String],
  city: Option[String],
  state: Option[String],
)
