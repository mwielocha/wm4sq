package io.mwielocha.wm4sq.model.forsquare

import io.circe.generic.JsonCodec


@JsonCodec
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
