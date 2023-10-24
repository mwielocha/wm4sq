package io.mwielocha.wm4sq.routes

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.server.Directives.get
import akka.http.scaladsl.server.Directives.path
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.Unmarshal
import cats.data.OptionT
import cats.implicits._
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import io.circe.Json
import io.getquill.context.jasync.Decoders
import io.getquill.mirrorContextWithQueryProbing.Decoder
import io.getquill.MappedEncoding
import io.getquill.MysqlJAsyncContext
import io.getquill.SnakeCase
import io.mwielocha.wm4sq.ical.ICalendarSupport
import io.mwielocha.wm4sq.ical.Implicits._
import io.mwielocha.wm4sq.model.forsquare.Checkins
import io.mwielocha.wm4sq.model.AccessToken
import io.mwielocha.wm4sq.model.User

import scala.concurrent.Future

class Routes(config: Config) extends ErrorAccumulatingCirceSupport with ICalendarSupport with LazyLogging {

  private val ctx = new MysqlJAsyncContext(SnakeCase, "db")

  private val host         = config.getString("host")
  private val clientId     = config.getString("4sq.client_id")
  private val clientSecret = config.getString("4sq.client_secret")

  import ctx._

  def apply(): Route =
    extractMaterializer { implicit mat =>
      extractActorSystem { implicit system =>
        extractExecutionContext { implicit ec =>
          path("create") {
            get {
              complete {

                for {
                  user  <- Future.successful(User())
                  q      = quote {
                             query[User]
                               .insertValue(lift(user))
                           }
                  _     <- ctx.run(q)
                  params = Uri.Query(
                             "client_id"     -> clientId,
                             "response_type" -> "code",
                             "redirect_uri"  -> s"$host/finalize?id=${user.id}"
                           )
                  uri    = Uri("https://foursquare.com/oauth2/authenticate").withQuery(params)
                } yield HttpResponse(status = StatusCodes.TemporaryRedirect, headers = Location(uri) :: Nil)
              }
            }
          } ~ (path("finalize") & parameter("code".as[String]) & parameter("id".as[String])) { (code, id) =>
            complete {

              val params = Uri.Query(
                "client_id"     -> clientId,
                "client_secret" -> clientSecret,
                "grant_type"    -> "authorization_code",
                "redirect_uri"  -> s"$host/finalize?id=$id",
                "code"          -> code
              )

              for {
                response    <- Http().singleRequest(
                                 HttpRequest(
                                   method = HttpMethods.GET,
                                   uri = Uri("https://foursquare.com/oauth2/access_token").withQuery(params)
                                 )
                               )
                _            = logger.info(s"Oauth response: " + response)
                accessToken <- Unmarshal(response.entity).to[AccessToken]
                q            = quote {
                                 query[User]
                                   .filter(_.id == lift(id))
                                   .update(_.token -> lift(Option(accessToken.access_token)))
                               }
                _           <- ctx.run(q)
              } yield Json.fromString(id)
            }

          } ~ path(Segment) { id =>
            get {
              complete {
                val q = quote {
                  query[User]
                    .filter(_.id == lift(id))
                    .take(1)
                }

                (for {
                  users    <- OptionT.liftF(ctx.run(q))
                  user     <- OptionT.fromOption[Future](users.headOption)
                  token    <- OptionT.fromOption[Future](user.token)
                  params    = Uri.Query(
                                "v"           -> "20190423",
                                "oauth_token" -> token,
                                "limit"       -> "100000"
                              )
                  request   = HttpRequest(
                                method = HttpMethods.GET,
                                uri = Uri("https://api.foursquare.com/v2/users/self/checkins").withQuery(params)
                              )
                  response <- OptionT.liftF(Http().singleRequest(request))
                  checkins <- OptionT.liftF {
                                Unmarshal(response.entity).to[Checkins]
                              }
                  _         = logger.info(s"Got ${checkins.count} checkins...")
                } yield checkins.asCalendar).value
              }
            }
          }
        }
      }
    }
}
