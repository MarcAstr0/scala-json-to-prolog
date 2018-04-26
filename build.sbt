name := "json-to-prolog"
organization := "com.marcastr0"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies ++= {
  val liftVersion = "3.2.0"
  Seq(
    "org.scalactic" %% "scalactic"   % "3.0.4",
    "org.scalatest" %% "scalatest"   % "3.0.4"     % "test",
    "io.spray"      %% "spray-json"  % "1.3.3",
    "net.liftweb"   %% "lift-webkit" % liftVersion % "compile"
  )
}

licenses += ("MIT", url("https://github.com/MarcAstr0/scala-json-to-prolog/blob/master/LICENSE"))