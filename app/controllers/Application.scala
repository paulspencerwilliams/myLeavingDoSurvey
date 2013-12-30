package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.Acceptance
import models.Availability
import models.Activity


object Application extends Controller {

  val acceptanceForm: Form[Acceptance]  = Form(
    mapping(
      "name" -> text,
      "availabilities" -> seq(
          mapping(
            "date" -> text,
            "available" -> boolean
          )
          (Availability.apply)(Availability.unapply)
        ),
      "activities" -> seq(
          mapping(
            "name" -> text,
            "acceptable" -> boolean
          )
          (Activity.apply)(Activity.unapply)
        )
      )
      (Acceptance.apply)(Acceptance.unapply)
    )

  def index = Action {
    val blankAcceptance = Acceptance("",
      availabilities = List(
        Availability("30th Jan (evening)", false),
        Availability("31st Jan (evening)", false),
        Availability("6th Jan (afternoon)", false),
        Availability("6th Jan (evening)", false)
      ),
      activities = List(
        Activity("Mountain biking", false),
        Activity("Tobogganing", false),
        Activity("Go karting", false)
      )
    )
    Ok(views.html.acceptanceform(acceptanceForm.fill(blankAcceptance)))
  }

  def accept = Action { implicit request =>
    acceptanceForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.acceptanceform(formWithErrors)), 
      value => {
        Acceptance.create(value)
        Redirect(routes.Application.cheers)
      }
    )
  }

  def cheers = Action {
    Ok(views.html.cheers())
  }

}
