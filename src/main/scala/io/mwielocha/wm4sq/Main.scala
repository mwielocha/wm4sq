package io.mwielocha.wm4sq

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import io.mwielocha.wm4sq.routes.Routes

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

object Main extends App {

  implicit val system: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext = system.dispatcher

  val config = ConfigFactory.load()

  val routes = new Routes(config)

  for {
    _ <- Http().newServerAt(
      config.getString("http.interface"),
      config.getInt("http.port")
    ).bind(routes())
  } yield Await.result(system.whenTerminated, Duration.Inf)
}
