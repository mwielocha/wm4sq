package io.mwielocha.wm4sq.model
import io.circe.generic.JsonCodec

import java.util.UUID

object User {

  object Id {
    def apply(): String =
      UUID.randomUUID().toString
  }
}

@JsonCodec
case class User(
  id: String = User.Id(),
  token: Option[String] = None
)
