/*
 * Copyright 2024 HM Revenue & Customs
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

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future.successful

import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import uk.gov.hmrc.ciaomultisegmentapi.config.AppContext
import uk.gov.hmrc.ciaomultisegmentapi.views.txt.definition

@Singleton
class DefinitionController @Inject() (appContext: AppContext, cc: ControllerComponents) extends BackendController(cc) {

  def get(): Action[AnyContent] = Action.async {
    successful(
      Ok(definition(appContext.apiContext)).withHeaders(CONTENT_TYPE -> JSON)
    )
  }

}
