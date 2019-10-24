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
//  ScalaLib("charts", "0.8.0"),
)
libraryDependencies ++= udashLibs map { lib => "io.udash" %%% s"udash-${lib.name}" % lib.version }
// Binding.scala
libraryDependencies += "com.thoughtworks.binding" %%% "dom" % "latest.release"


// JS dependencies
skip in packageJSDependencies := false
// Jquery
jsDependencies += "org.webjars" % "jquery" % "3.3.1" / "3.3.1/jquery.js" minified "3.3.1/jquery.min.js"
// Highcharts
//val highchartsVersion = "5.0.10"
//val highlightsLibs = Seq[JSLib](
//  JSLib("highcharts.src.js", highchartsVersion, "jquery.js"),
//  JSLib("highcharts-3d.src.js", highchartsVersion, "highcharts-3d.src.js"),
//  JSLib("highcharts-more.src.js", highchartsVersion, "highcharts-more.src.js"),
//  JSLib("modules/exporting.src.js", highchartsVersion, "modules/exporting.src.js"),
//  JSLib("modules/drilldown.src.js", highchartsVersion, "modules/drilldown.src.js"),
//  JSLib("modules/heatmap.src.js", highchartsVersion, "modules/heatmap.src.js"),
//)
//jsDependencies ++= highlightsLibs map { lib =>
//  "org.webjars" % "highcharts" % lib.version / s"${lib.version}/${lib.name}" minified s"${lib.version}/${lib.name}" dependsOn lib.dependency
//}
