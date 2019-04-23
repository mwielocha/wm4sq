package io.mwielocha.wm4sq.ical

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.MediaTypes._
import biweekly.{Biweekly, ICalendar}


trait ICalendarSupport {

  implicit def icalMarshaller: ToEntityMarshaller[ICalendar] =
    Marshaller.StringMarshaller.wrap(`text/calendar`) {
      calendar =>
        Biweekly.write(Seq(calendar): _*).go()
    }
}
