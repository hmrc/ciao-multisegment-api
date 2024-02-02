import sbt._
import play.core.PlayVersion
import play.sbt.PlayImport._

object AppDependencies {
  def apply() = appDependencies

  lazy val appDependencies: Seq[ModuleID] = compile ++ test

  lazy val bootStrapVersion = "8.4.0"

  lazy val compile: Seq[ModuleID] = Seq(
    ws,
    "uk.gov.hmrc"             %% "bootstrap-backend-play-30"    % bootStrapVersion
  )

  lazy val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-30"           % bootStrapVersion,
    "org.mockito"             %% "mockito-scala-scalatest"          % "1.17.29"
  ).map(_ % "test")

}
