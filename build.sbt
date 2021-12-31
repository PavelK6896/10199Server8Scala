ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.0"

lazy val Http4sVersion = "1.0.0-M30"
lazy val CirceVersion = "0.14.1"

lazy val root = (project in file("."))
  .settings(
    name := "10199Server8Scala",
    idePackagePrefix := Some("app.web.pavelk.server8"),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
    )
  )


