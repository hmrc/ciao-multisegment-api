/*
 * Copyright 2021 HM Revenue & Customs
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

import akka.stream.Materializer
import play.api.libs.json.Json
import play.api.test.{FakeRequest, StubControllerComponentsFactory}
import play.api.test.Helpers._
import uk.gov.hmrc.ciaomultisegmentapi.models.JsonFormatters.formatWelcomeMessage
import uk.gov.hmrc.ciaomultisegmentapi.models.WelcomeMessage
import play.api.test.NoMaterializer
import uk.gov.hmrc.ciaomultisegmentapi.AsyncHmrcSpec

class CountryControllerSpec extends AsyncHmrcSpec with StubControllerComponentsFactory {

  private implicit val materializer: Materializer = NoMaterializer

  private val controller = new CountryController(stubControllerComponents())

  private val country = "Chile"
  private val city = "Santiago"

  "CountryController.showCountry()" should {

    "respond with the expected message" in {

      List(GET, PUT).foreach { verb =>

        val request = FakeRequest(method = verb, path = s"/country-details/$country/display")

        val result = controller.showCountry(country)(request)

        status(result) shouldBe OK

        val expectedAnswer = WelcomeMessage(s"$verb: You are in $country!")

        contentAsJson(result) shouldBe Json.toJson(expectedAnswer)
      }

    }

  }

  "CountryController.showCountryAndCity()" should {

    "respond with the expected message" in {

      List(POST, DELETE).foreach { verb =>

        val request = FakeRequest(method = verb, path = s"/country-details/$country/$city")

        val result = controller.showCountryAndCity(country, city)(request)

        status(result) shouldBe OK

        val expectedAnswer = WelcomeMessage(s"$verb: You are in $city, which is inside $country!")

        contentAsJson(result) shouldBe Json.toJson(expectedAnswer)
      }

    }

  }

}
