package io.mwielocha.wm4sq.model
import java.util.UUID

import io.circe.derivation._
import io.circe.{Decoder, Encoder}

object User {

  object Id {
    def apply(): String =
      UUID.randomUUID().toString
  }

  implicit val encode: Encoder[User] = deriveEncoder
  implicit val decode: Decoder[User] = deriveDecoder
}

case class User(
  id: String = User.Id(),
  token: Option[String] = None
)
