package io.mwielocha.wm4sq.routes

import akka.http.scaladsl.server.Directives.{get, path, _}
import akka.http.scaladsl.server.Route
import com.typesafe.config.Config
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.getquill.{MysqlAsyncContext, SnakeCase}
import io.mwielocha.wm4sq.model.User

import scala.concurrent.Future

class Routes(config: Config) extends ErrorAccumulatingCirceSupport {

  private val ctx = new MysqlAsyncContext(SnakeCase, "db")

  import ctx._

  def apply(): Route =
    extractExecutionContext { implicit ec =>
      path("create") {
        get {
          complete {

            for {
              user <- Future.successful(User())
              q = quote {
                query[User]
                  .insert(lift(user))
              }
              _ <- ctx.run(q)
            } yield user
          }
        }
      } ~ path(Segment) { id =>
        get {
          complete {
            val q = quote {
              query[User]
                .filter(_.id == lift(id))
                .take(1)
            }

            for {
              result <- ctx.run(q)
            } yield result.headOption
          }
        }
      }
    }
}
