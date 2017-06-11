name := "sparkStreamDT"

version := "1.0"

scalaVersion := "2.11.9"


val sparkVersion = "2.1.0"
val jacksonVersion = "2.8.6"

resolvers += "MavenRepository" at "https://mvnrepository.com/"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-compiler" % "2.11.9",
  "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4"
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
  "com.fasterxml.jackson.module" % "jackson-module-paranamer" % jacksonVersion,
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.4",
  "org.apache.spark" %% "spark-core" % sparkVersion %"provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion %"provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion %"provided",
  "info.cukes" % "cucumber-scala_2.11" % "1.2.4" % "test",
  "info.cukes" % "cucumber-junit" % "1.2.4" % "test",
  "info.cukes" % "cucumber-jvm" % "1.2.4" % "test",
  "junit" % "junit" % "4.12" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"

)


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

enablePlugins(CucumberPlugin)
CucumberPlugin.glue := ""
testOptions in Test := Seq(Tests.Filter(name => name.toLowerCase().contains("runtests")))
