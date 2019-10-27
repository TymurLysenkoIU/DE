enablePlugins(ScalaJSPlugin)
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

name := "de-assignment"
organization := "me.sitiritis"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.12.8"

scalaJSUseMainModuleInitializer := true
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()


// Scala dependencies
// Udash
val udashLibs = Seq(
  ScalaLib("jquery", "3.0.2"),
)
libraryDependencies ++= udashLibs map { lib => "io.udash" %%% s"udash-${lib.name}" % lib.version }
// Binding.scala
libraryDependencies += "com.thoughtworks.binding" %%% "dom" % "11.8.1+36-f6ab2503"
// Plotly
libraryDependencies += "org.plotly-scala" %%% "plotly-render" % "0.5.2"
// CSS
libraryDependencies += "com.github.japgolly.scalacss" %%% "core" % "0.5.3"


// JS dependencies
skip in packageJSDependencies := false
// Jquery
jsDependencies += ("org.webjars" % "jquery" % "3.3.1" / "3.3.1/jquery.js" minified "3.3.1/jquery.min.js").commonJSName("jquery")
// Plotly
jsDependencies += ("org.webjars.bower" % "plotly.js" % "1.41.3" / "plotly.min.js").commonJSName("Plotly")
