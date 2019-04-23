package io.mwielocha.wm4sq.model

import io.circe.{Decoder, Encoder}
import io.circe.derivation.{deriveDecoder, deriveEncoder}

object AccessToken {
  implicit val encode: Encoder[AccessToken] = deriveEncoder
  implicit val decode: Decoder[AccessToken] = deriveDecoder
}

case class AccessToken(
  access_token: String
)
