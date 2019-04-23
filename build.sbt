
// Generated with scalagen

scalacOptions in ThisBuild := Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-Ypartial-unification",
  "-Yrangepos",
  "-language:postfixOps",
  "-Xcheckinit",
  "-encoding",
  "utf8"
)

lazy val root = (project in file(".")).
  settings(
    name := "wm4sq",
    version := "1.0",
    scalaVersion := "2.12.6"
  )

mainClass in (Compile, run) := Some("io.mwielocha.wm4sq.Main")

val quillVersion             = "3.1.0"
val akkaHttpVersion          = "10.1.7"
val akkaVersion              = "2.5.21"
val akkaHttpJsonVersion      = "1.25.2"
val circeDerivationVersion   = "0.11.0-M1"
val macwireVersion           = "2.3.2"
val circeVersion             = "0.11.1"
val catsVersion              = "1.6.0"
val logbackVersion           = "1.2.3"
val scalaLoggingVersion      = "3.9.2"

libraryDependencies ++= Seq(
  "io.getquill" %% "quill-async-mysql" % "3.1.0",
  "net.sf.biweekly" % "biweekly" % "0.6.3",
  "ch.qos.logback"             % "logback-classic"         % logbackVersion,
  "com.typesafe.scala-logging" %% "scala-logging"          % scalaLoggingVersion,
  "de.heikoseeberger"          %% "akka-http-circe"        % akkaHttpJsonVersion,
  "com.typesafe.akka"          %% "akka-http"              % akkaHttpVersion,
  "de.heikoseeberger"          %% "akka-http-circe"        % akkaHttpJsonVersion,
  "com.typesafe.akka"          %% "akka-http-xml"          % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-stream"            % akkaVersion,
  "io.circe"                   %% "circe-core"             % circeVersion,
  "io.circe"                   %% "circe-derivation"       % circeDerivationVersion,
  "org.typelevel"              %% "cats-core"              % catsVersion,
  "io.circe"                   %% "circe-generic"          % circeVersion,
  "io.circe"                   %% "circe-parser"           % circeVersion,
  "io.circe"                   %% "circe-literal"          % circeVersion,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

