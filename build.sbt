name := "ScalaqDemo"

version := "1.0"

lazy val `scalaqdemo` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test )

libraryDependencies += "org.revenj" % "revenj-core" % "0.9.9"

resolvers += "NGS Releases" at "http://ngs.hr/nexus/content/repositories/releases/"

libraryDependencies += "hr.ngs.templater" %% "templater" % "2.3.2"


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

