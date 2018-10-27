name := """play-slick-example"""

version := "1.0"

scalaVersion := "2.12.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)


lazy val playVersion = "2.6.10"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-json" % playVersion,
  "com.typesafe.play" %% "play-slick" % "3.0.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",
  "com.h2database" % "h2" % "1.4.187",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc4",
  specs2 % Test
)

routesGenerator := InjectedRoutesGenerator

javaOptions in Test += "-Dconfig.file=conf/test.conf"
