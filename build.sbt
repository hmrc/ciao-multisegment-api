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
import uk.gov.hmrc.versioning.SbtGitVersioning
import uk.gov.hmrc.DefaultBuildSettings.addTestReportOption
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

import uk.gov.hmrc._
import DefaultBuildSettings._


val appName = "ciao-multisegment-api"

lazy val compile = Seq(
  ws,
  "uk.gov.hmrc" %% "bootstrap-play-25" % "1.7.0",
  "uk.gov.hmrc" %% "play-hmrc-api" % "3.0.0"
)

lazy val testScope = "test"

lazy val test = Seq(
  "uk.gov.hmrc" %% "hmrctest" % "3.0.0" % testScope,
  "org.scalatest" %% "scalatest" % "3.0.4" % testScope,
  "org.pegdown" % "pegdown" % "1.6.0" % testScope,
  "com.typesafe.play" %% "play-test" % PlayVersion.current % testScope
)

lazy val appDependencies: Seq[ModuleID] = compile ++ test

lazy val microservice = Project(appName, file("."))
  .enablePlugins(_root_.play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin)
  .settings(scalaSettings: _*)
  .settings(publishingSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(unmanagedResourceDirectories in Compile += baseDirectory.value / "resources")
  .settings(
    name := appName,
    targetJvm := "jvm-1.8",
    scalaVersion := "2.11.11",
    libraryDependencies ++= appDependencies,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(warnScalaVersionEviction = false)
  )
  .settings(
    testOptions in Test := Seq(Tests.Filter(_ => true)), // this removes duplicated lines in the HTML test reports
    unmanagedSourceDirectories in Test := Seq((baseDirectory in Test).value / "test"),
    addTestReportOption(Test, "test-reports")
  )
  .settings(resolvers ++= Seq(
    Resolver.bintrayRepo("hmrc", "releases"),
    Resolver.jcenterRepo
  ))

// Coverage configuration
coverageMinimum := 22
coverageFailOnMinimum := true
coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo;views.*;uk.gov.hmrc.ciaomultisegmentapi.config.*"
