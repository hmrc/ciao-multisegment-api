import play.sbt.PlayImport._
import play.core.PlayVersion
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin
import uk.gov.hmrc.DefaultBuildSettings.addTestReportOption
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings
import uk.gov.hmrc._
import DefaultBuildSettings._
import sbt.Tests.{Group, SubProcess}

import bloop.integrations.sbt.BloopDefaults

val appName = "ciao-multisegment-api"

val bootstrapVersion = "7.15.0"

scalaVersion := "2.13.12"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val compile = Seq(
  ws,
  "uk.gov.hmrc"             %% "bootstrap-backend-play-28"    % bootstrapVersion,
  "uk.gov.hmrc"             %% "play-hmrc-api"                % "7.1.0-play-28"
)

lazy val test = Seq(
  "uk.gov.hmrc"             %% "bootstrap-test-play-28"           % bootstrapVersion,
  "com.vladsch.flexmark"     % "flexmark-all"                     % "0.62.2",
  "com.github.tomakehurst"  %  "wiremock-jre8-standalone"         % "2.31.0",
  "org.mockito"             %% "mockito-scala-scalatest"          % "1.17.29",
  "org.scalatest"           %% "scalatest"                        % "3.2.17"
).map(_ % "test")

lazy val appDependencies: Seq[ModuleID] = compile ++ test

lazy val microservice = Project(appName, file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtDistributablesPlugin)
  .settings(scalaSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(Compile / unmanagedResourceDirectories += baseDirectory.value / "resources")
  .settings(ScoverageSettings())
  .settings(
    name := appName,
    majorVersion := 0,
    libraryDependencies ++= appDependencies
  )
  .settings(inConfig(Test)(BloopDefaults.configSettings))
  .settings(
    Test / testOptions := Seq(Tests.Filter(_ => true)), // this removes duplicated lines in the HTML test reports
    Test / unmanagedSourceDirectories += baseDirectory.value / "test",
    Test / unmanagedSourceDirectories += baseDirectory.value / "testcommon",
    addTestReportOption(Test, "test-reports")
  )
  .settings(
    scalacOptions ++= Seq(
      "-Wconf:cat=unused&src=views/.*\\.scala:s",
      "-Wconf:cat=unused&src=.*RoutesPrefix\\.scala:s",
      "-Wconf:cat=unused&src=.*Routes\\.scala:s",
      "-Wconf:cat=unused&src=.*ReverseRoutes\\.scala:s"
    )
  )

Global / bloopAggregateSourceDependencies := true
Global / bloopExportJarClassifiers := Some(Set("sources"))

commands ++= Seq(
  Command.command("run-all-tests") { state => "test" :: state },

  Command.command("clean-and-test") { state => "clean" :: "compile" :: "run-all-tests" :: state },

  // Coverage does not need compile !
  Command.command("pre-commit") { state => "clean" :: "scalafmtAll" :: "scalafixAll" :: "coverage" :: "run-all-tests" :: "coverageOff" :: "coverageAggregate" :: state }
)
