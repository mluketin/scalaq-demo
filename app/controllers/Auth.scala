package controllers


import models.{EmailValidator, LoginModel}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

class Auth  extends Controller {
  val loginForm = Form(
    tuple(
    "email" -> nonEmptyText,
    "pass" -> nonEmptyText
    )
      verifying("Invalid email address", data => EmailValidator.isValid(data._1))
      verifying("Email or password does not match!", result => result match {
      case(email, pass) => LoginModel.userExists(email, pass).isDefined
      })
  )

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def logout = Action { implicit request =>
    Redirect(routes.Auth.login()).withNewSession
  }

  def handleButtonLogin = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      loginData => {
        Redirect(routes.Home.index()).withSession("email" -> loginData._1)
      }
    )
  }

  def registration = Action {
    Redirect(routes.Registration.index())
  }
}
