name := "scala-irc-client"

version := "1.0-SNAPSHOT"

organization := "com.github.jankroken"

scalaVersion := "2.10.3"

// assemblySettings

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "1.0.0-M7", // previously M6
  "org.scalatest" %% "scalatest" % "2.0" % "test", // previously 1.9.2
  "com.typesafe.akka" %% "akka-actor" % "2.2.3"
)

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

// Sources should also be copied to output, so the sample code, for the viewer,
// can be loaded from the same file that is used to execute the example
unmanagedResourceDirectories in Compile <+= baseDirectory { _/"src/main/scala"}

// Set the prompt (for this build) to include the project id.
shellPrompt := { state => System.getProperty("user.name") + ":" + Project.extract(state).currentRef.project + "> " }

// Add JavaFX 2.0 to classpath
unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/jfxrt.jar"))

// Run in separate VM, so there are no issues with double initialization of JavaFX
fork := true

fork in Test := true

scalacOptions += "-feature"

