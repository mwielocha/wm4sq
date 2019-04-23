package io.mwielocha.wm4sq.model.forsquare

import io.circe.{Decoder, Encoder}
import io.circe.derivation.{deriveDecoder, deriveEncoder}

object Venue {
  implicit val encode: Encoder[Venue] = deriveEncoder
  implicit val decode: Decoder[Venue] = deriveDecoder
}

case class Venue(
  id: String,
  name: String,
  location: Location
)
