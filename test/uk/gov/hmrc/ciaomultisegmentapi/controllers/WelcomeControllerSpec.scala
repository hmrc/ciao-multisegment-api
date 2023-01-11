/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ciaomultisegmentapi.controllers

import akka.stream.testkit.NoMaterializer

import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, StubControllerComponentsFactory}
import uk.gov.hmrc.ciaomultisegmentapi.AsyncHmrcSpec
import uk.gov.hmrc.ciaomultisegmentapi.models.JsonFormatters.formatWelcomeMessage
import uk.gov.hmrc.ciaomultisegmentapi.models.WelcomeMessage

class WelcomeControllerSpec extends AsyncHmrcSpec with StubControllerComponentsFactory {

  private implicit val materializer = NoMaterializer

  private val controller = new WelcomeController(stubControllerComponents())

  "WelcomeController" should {

    "respond with the expected message when calling GET / " in {

      val request = FakeRequest(method = "GET", path = "/")

      val result = controller.welcome()(request)

      status(result) shouldBe OK

      val expectedAnswer = WelcomeMessage("Ciao!")

      contentAsJson(result) shouldBe Json.toJson(expectedAnswer)
    }

    "respond with the expected message when calling GET /:friend " in {

      val friend = "Valentino"

      val request = FakeRequest(method = "GET", path = s"/$friend")

      val result = controller.welcomeFriend(friend)(request)

      status(result) shouldBe OK

      val expectedAnswer = WelcomeMessage(s"Ciao $friend!")

      contentAsJson(result) shouldBe Json.toJson(expectedAnswer)
    }

    "respond with the expected message when calling GET /:friend/:city " in {

      val friend = "Alvise"
      val city   = "Paris"

      val request = FakeRequest(method = "GET", path = s"/$friend/$city")

      val result = controller.welcomeFriendCity(friend, city)(request)

      status(result) shouldBe OK

      val expectedAnswer = WelcomeMessage(s"Ciao $friend! Welcome to $city!")

      contentAsJson(result) shouldBe Json.toJson(expectedAnswer)
    }

  }

}
