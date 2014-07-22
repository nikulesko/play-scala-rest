name := """play-scala-rest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies += "org.squeryl" % "squeryl_2.11" % "0.9.5-7"

libraryDependencies += "com.h2database" % "h2" % "1.3.174"

libraryDependencies += "commons-dbcp" % "commons-dbcp" % "1.3"

libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)