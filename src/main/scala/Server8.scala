
package app.web.pavelk.server8

import cats.*
import cats.data.{NonEmptyList, OptionT}
import cats.effect.*
import cats.implicits.*
import io.circe.generic.auto.*
import io.circe.syntax.*
import org.http4s.*
import org.http4s.CacheDirective.`no-cache`
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.circe.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.dsl.io.*
import org.http4s.headers.*
import org.http4s.implicits.*
import org.http4s.server.*
import org.http4s.server.staticcontent.*
import org.typelevel.ci.CIString

import java.io.File
import java.time.InstantSource.system
import java.time.Year
import java.util.UUID
import java.util.concurrent.*
import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.util.Try


object Server8 extends IOApp {

  import scala.concurrent.ExecutionContext.global

  override def run(args: List[String]): IO[ExitCode] = {

    val apis = Router(
      "/" -> Server8.getIndex[IO],
      "/info" -> Server8.getInfo[IO],
    ).orNotFound

    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(apis)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }

  def getIndex[F[_] : Sync]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl.*
    HttpRoutes.of[F] {
      case request@GET -> Root =>
        StaticFile
          .fromResource("/pages/index.html", Some(request))
          .getOrElseF(NotFound())
    }
  }

  def getInfo[F[_] : Monad]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root =>
        Ok("Sever 8 ok!")
    }
  }

  def allRoutes[F[_] : Concurrent]: HttpRoutes[F] = {
    getInfo[F]
  }

  def allRoutesComplete[F[_] : Concurrent]: HttpApp[F] = {
    allRoutes.orNotFound
  }

}
