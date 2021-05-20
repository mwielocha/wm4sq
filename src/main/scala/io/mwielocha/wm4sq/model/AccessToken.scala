package io.mwielocha.wm4sq.model

import io.circe.generic.JsonCodec

@JsonCodec
case class AccessToken(
  access_token: String
)
