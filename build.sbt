/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

lazy val compile = Seq(
  ws,
  "uk.gov.hmrc" %% "bootstrap-backend-play-26" % "5.10.0",
  "uk.gov.hmrc" %% "play-hmrc-api" % "6.4.0-play-26"
)

lazy val test = Seq(
  "uk.gov.hmrc" %% "hmrctest" % "3.9.0-play-26",
  "org.pegdown" % "pegdown" % "1.6.0",
  "org.mockito" %% "mockito-scala-scalatest" % "1.7.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.3",
  "com.typesafe.play" %% "play-test" % PlayVersion.current,
  "com.github.tomakehurst" % "wiremock-jre8-standalone" % "2.27.2"
).map(_ % "test")

lazy val appDependencies: Seq[ModuleID] = compile ++ test

lazy val microservice = Project(appName, file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtDistributablesPlugin)
  .settings(scalaSettings: _*)
  .settings(publishingSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(unmanagedResourceDirectories in Compile += baseDirectory.value / "resources")
  .settings(ScoverageSettings())
  .settings(
    name := appName,
    targetJvm := "jvm-1.8",
    majorVersion := 0,
    scalaVersion := "2.12.12",
    libraryDependencies ++= appDependencies,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(warnScalaVersionEviction = false)
  )
  .settings(inConfig(Test)(BloopDefaults.configSettings))
  .settings(
    Test / testOptions := Seq(Tests.Filter(_ => true)), // this removes duplicated lines in the HTML test reports
    Test / unmanagedSourceDirectories += baseDirectory.value / "test",
    Test / unmanagedSourceDirectories += baseDirectory.value / "testcommon",
    addTestReportOption(Test, "test-reports")
  )
