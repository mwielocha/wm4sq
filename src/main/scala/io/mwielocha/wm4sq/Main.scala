package io.mwielocha.wm4sq

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import io.mwielocha.wm4sq.routes.Routes

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration

object Main extends App {

  implicit val system: ActorSystem = ActorSystem()
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  val config = ConfigFactory.load()

  val routes = new Routes(config)

  for {
    _ <- Http().bindAndHandle(
      routes(),
      config.getString("http.interface"),
      config.getInt("http.port")
    )
  } yield Await.result(system.whenTerminated, Duration.Inf)
}
