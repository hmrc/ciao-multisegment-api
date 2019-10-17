/*
 * Copyright 2019 HM Revenue & Customs
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
import play.api.http.Status
import play.api.libs.json.JsValue
import play.api.mvc.Result
import play.api.test.FakeRequest
import uk.gov.hmrc.ciaomultisegmentapi.config.AppContext
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

class DefinitionControllerSpec extends UnitSpec with WithFakeApplication {

  trait Setup {
    implicit val materializer: Materializer = fakeApplication.materializer
    val request = FakeRequest()
    val controller = new DefinitionController(fakeApplication.injector.instanceOf[AppContext])
  }

  "get" should {
    "respond with the API definition" in new Setup {
      val result: Result = await(controller.get()(request))

      status(result) shouldBe Status.OK
      val api: JsValue = (jsonBodyOf(result) \ "api").as[JsValue]
      (api \ "name").as[String] shouldBe "Ciao multi-segment context API"
      (api \ "context").as[String] shouldBe "ciao/hey/welcome"
    }
  }
}
