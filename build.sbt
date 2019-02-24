

name := """play-slick-example"""

version := "1.0"

scalaVersion := "2.12.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)


lazy val playVersion = "2.7.0"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-json" % playVersion,
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
  "com.h2database" % "h2" % "1.4.187",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc4",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test,
  "org.mockito" % "mockito-core" % "1.10.19" % Test,
  specs2 % Test
)

routesGenerator := InjectedRoutesGenerator

javaOptions in Test += "-Dconfig.file=conf/test.conf"
