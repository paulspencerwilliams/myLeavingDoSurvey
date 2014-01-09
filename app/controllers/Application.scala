package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.Acceptance
import models.Availability


object Application extends Controller {

  val acceptanceForm: Form[Acceptance]  = Form(
    mapping(
      "name" -> text(minLength = 3, maxLength = 50),
      "availabilities" -> seq(
          mapping(
            "date" -> text,
            "available" -> boolean
          )
          (Availability.apply)(Availability.unapply)
        )
      )
      (Acceptance.apply)(Acceptance.unapply)
    )

  def index = Action {
    val blankAcceptance = Acceptance("",
      availabilities = List(
        Availability("Thur 30th Jan", false),
        Availability("Fri 31st Jan", false),
        Availability("Fri 7th Jan", false)
      )
    )
    Ok(views.html.acceptanceform(acceptanceForm.fill(blankAcceptance)))
  }

  def accept = Action { implicit request =>
    acceptanceForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.acceptanceform(formWithErrors)), 
      value => {
        Acceptance.create(value)

        Redirect(routes.Application.cheers).withSession("name" -> value.name)
      }
    )
  }

  def cheers = Action { implicit request =>
    session.get("name").map { name =>
      Ok(views.html.cheers(name))
    }.getOrElse {
      Ok(views.html.cheers("Not set"))
    }
  }

}
