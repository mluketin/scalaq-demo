package controllers

import models.{EmailValidator, RegistrationModel}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

class Registration extends Controller {
  val registerForm = Form(
    tuple(
      "email" -> nonEmptyText,
      "pass" -> nonEmptyText,
      "repass" -> nonEmptyText
    )
      verifying("Invalid email address", data => EmailValidator.isValid(data._1))
      verifying("Email not avaible", result => result match {
      case (email, pass, repass) => RegistrationModel.emailInUse(email, pass, repass).isDefined
    })
      verifying("Passwords don't match", data => data._2 == data._3)
  )

  def index() = Action { implicit request =>
    Ok(views.html.register(registerForm))
  }

  def register = Action { implicit request =>
    registerForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.register(formWithErrors)),
      loginData => {
        if( RegistrationModel.registerUser(loginData)) {
          Redirect(routes.Auth.login()).flashing(
            "success" -> "Registration successful")
        } else {
          Ok(views.html.register(registerForm)).flashing(
            "bad" -> "Registration unsuccessful")
        }
      }
    )
  }
}
