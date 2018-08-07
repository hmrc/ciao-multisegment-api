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

package uk.gov.hmrc.ciaomultisegmentapi.controllers

import javax.inject.Singleton
import play.api.libs.json.Json.toJson
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.ciaomultisegmentapi.models.WelcomeMessage
import uk.gov.hmrc.ciaomultisegmentapi.models.JsonFormatters.formatWelcomeMessage
import uk.gov.hmrc.play.bootstrap.controller.BaseController

import scala.concurrent.Future.successful

@Singleton()
class CountryController extends BaseController {

  def showCountry(country: String): Action[AnyContent] = Action.async { implicit request =>
    successful(
      Ok(toJson(WelcomeMessage(s"${request.method}: You are in $country!")))
    )
  }

  def showCountryAndCity(country: String, city: String): Action[AnyContent] = Action.async { implicit request =>
    successful(
      Ok(toJson(WelcomeMessage(s"${request.method}: You are in $city, which is inside $country!")))
    )
  }

}