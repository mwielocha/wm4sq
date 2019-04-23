
// Generated with scalagen

lazy val root = (project in file(".")).
  settings(
    name := "wm4sq",
    version := "1.0",
    scalaVersion := "2.12.6"
  )

//mainClass in (Compile, run) := Some("...")

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )

