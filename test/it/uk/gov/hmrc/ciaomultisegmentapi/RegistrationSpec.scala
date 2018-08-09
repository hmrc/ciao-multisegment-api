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

package uk.gov.hmrc.ciaomultisegmentapi

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.scalatest._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Helpers.{CONTENT_TYPE, JSON}
import play.api.test.TestServer
import uk.gov.hmrc.play.test.UnitSpec

class RegistrationSpec extends UnitSpec with BeforeAndAfterEach {

  private val serverPort = 12121
  private var server: TestServer = _

  private val serviceLocatorHost = "localhost"
  private val serviceLocatorPort: Int = sys.env.getOrElse("WIREMOCK", "9602").toInt
  private val serviceLocatorServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(serviceLocatorPort))
  private val serviceLocatorMock = new WireMock(serviceLocatorHost, serviceLocatorPort)

  private val expectedSubscriptionRequest =
    """
       |{
       |  "serviceName":"ciao-multisegment-api",
       |  "serviceUrl":"http://localhost:9654",
       |  "metadata": {
       |    "third-party-api":"true"
       |  }
       |}
    """.stripMargin

  "ApplicationRegistration" should {
    "register to the service locator on application start-up" in {
      serviceLocatorServer.start()
      serviceLocatorMock.register(post(urlEqualTo("/registration")).willReturn(aResponse()))

      startServer()

      serviceLocatorMock.verifyThat(postRequestedFor(urlEqualTo("/registration"))
        .withHeader(CONTENT_TYPE, containing(JSON))
        .withRequestBody(equalToJson(expectedSubscriptionRequest)))
    }
  }

  private def startServer(): Unit = {
    server = new TestServer(serverPort, GuiceApplicationBuilder().build())
    server.start()
  }

  override def afterEach(): Unit = {
    stopServer()
    serviceLocatorServer.stop()
  }

  private def stopServer(): Unit = {
    server.stop()
  }

}
