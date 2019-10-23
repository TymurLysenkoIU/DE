enablePlugins(ScalaJSPlugin)

name := "de-assignment"
organization := "me.sitiritis"
version := "0.1"
scalaVersion := "2.12.8"

scalaJSUseMainModuleInitializer := true
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()

// Scala dependencies
//libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.7"
//libraryDependencies += "org.querki" %%% "jquery-facade" % "1.2"

// JS dependencies
skip in packageJSDependencies := false
//jsDependencies += "org.webjars" % "jquery" % "2.2.1" / "jquery.js" minified "jquery.min.js"
