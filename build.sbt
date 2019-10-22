// Settings of this project
ThisBuild / name := "de-assignment"
ThisBuild / organization := "me.sitiritis"
ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.13.1"
// Settings of this project


// ScalaFX
ThisBuild / libraryDependencies ++= Seq(
  "org.scalafx"   %% "scalafx"   % "12.0.2-R18",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test" //http://www.scalatest.org/download
)

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
ThisBuild / libraryDependencies ++= javaFXModules.map( m =>
  "org.openjfx" % s"javafx-$m" % "12.0.2" classifier osName
)

// Fork a new JVM for 'run' and 'test:run' to avoid JavaFX double initialization problems
ThisBuild / fork := true
// End ScalaFX


// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
ThisBuild / mainClass in (Compile, run) := Some("me.sitiritis.de.assignment.Main")

ThisBuild / shellPrompt := { _ => System.getProperty("user.name") + "> " }