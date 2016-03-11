name := "mock-http-server"

version := "0.0.1"

scalaVersion := "2.11.8"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

libraryDependencies += "org.eclipse.jetty.aggregate" % "jetty-all-server" % "8.1.19.v20160209"
