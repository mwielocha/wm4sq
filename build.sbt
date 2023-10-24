// Generated with scalagen

scalacOptions in ThisBuild := Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-language:existentials",
  "-language:higherKinds",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-Wconf:cat=other-match-analysis:error", // https://github.com/scala/scala/pull/8373
  "-Ymacro-annotations"
)

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name         := "wm4sq",
    version      := "1.0",
    organization := "io.mwielocha",
    scalaVersion := "2.13.6"
  )

packageDoc / publishArtifact := false

packageDoc / publishArtifact := false

Compile / mainClass := Some("io.mwielocha.wm4sq.Main")

val quillVersion        = "4.8.0"
val biweeklyVersion     = "0.6.6"
val akkaHttpVersion     = "10.2.3"
val akkaVersion         = "2.6.12"
val akkaHttpJsonVersion = "1.36.0"
val circeVersion        = "0.13.0"
val catsVersion         = "2.6.0"
val logbackVersion      = "1.2.3"
val scalaLoggingVersion = "3.9.3"

libraryDependencies ++= Seq(
  "io.getquill"                %% "quill-jasync-mysql" % quillVersion,
  "net.sf.biweekly"             % "biweekly"           % biweeklyVersion,
  "ch.qos.logback"              % "logback-classic"    % logbackVersion,
  "com.typesafe.scala-logging" %% "scala-logging"      % scalaLoggingVersion,
  "de.heikoseeberger"          %% "akka-http-circe"    % akkaHttpJsonVersion,
  "com.typesafe.akka"          %% "akka-http"          % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-stream"        % akkaVersion,
  "io.circe"                   %% "circe-core"         % circeVersion,
  "io.circe"                   %% "circe-generic"      % circeVersion,
  "io.circe"                   %% "circe-parser"       % circeVersion,
  "io.circe"                   %% "circe-literal"      % circeVersion,
  "org.typelevel"              %% "cats-core"          % catsVersion,
  "org.scalatest"              %% "scalatest"          % "3.2.9" % "test"
)
