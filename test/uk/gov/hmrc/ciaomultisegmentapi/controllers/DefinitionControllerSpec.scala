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
import play.api.libs.json.JsValue
import play.api.test.{FakeRequest, StubControllerComponentsFactory}
import play.api.test.Helpers._
import uk.gov.hmrc.ciaomultisegmentapi.config.AppContext
import play.api.test.NoMaterializer
import play.api.Configuration
import uk.gov.hmrc.ciaomultisegmentapi.AsyncHmrcSpec

class DefinitionControllerSpec extends AsyncHmrcSpec with StubControllerComponentsFactory {
  implicit val materializer: Materializer = NoMaterializer

  trait Setup {
    val request = FakeRequest()
    val mockConfig = mock[Configuration]
    val urlFromConfig = "ciao/hey/welcome"
    when(mockConfig.getOptional[String](*)(*)).thenReturn(Some(urlFromConfig))
    val appContext = new AppContext(mockConfig)
    val controller = new DefinitionController(appContext, stubControllerComponents())
  }

  "get" should {
    "respond with the API definition" in new Setup {
      val result = controller.get()(request)

      status(result) shouldBe OK
      val api: JsValue = (contentAsJson(result) \ "api").as[JsValue]
      (api \ "name").as[String] shouldBe "Ciao multi-segment context API"
      (api \ "context").as[String] shouldBe urlFromConfig
    }
  }
}
