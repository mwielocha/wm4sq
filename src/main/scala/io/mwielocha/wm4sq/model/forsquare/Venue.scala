package io.mwielocha.wm4sq.model.forsquare

import io.circe.generic.JsonCodec

@JsonCodec
case class Venue(
  id: String,
  name: String,
  location: Location
)
