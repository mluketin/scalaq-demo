package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._

case class LoginData(id: Long, email: String, pass: String)

class Application extends Controller {

  case class U(email: String, pass: String)

  var loggedUser: Option[U] = None

    val loginForm = Form(tuple(
      "email" -> nonEmptyText,
      "pass" -> nonEmptyText
    ))



  def index = Action {
    println("LOGGED USER: " + loggedUser)
    if(loggedUser == None)
      Redirect(routes.Application.login())
    else
      Redirect(routes.Application.home())
  }

  def login = Action {
    Ok(views.html.login(loginForm))
  }

  def handleButtonLogin = Action { implicit request =>
//    println(loginForm.data.get())
    loginForm.bindFromRequest.fold(
            errors => Redirect(routes.Application.login()),
      loginData => {
              println(User.userExists(User(loginData._1, loginData._2)))
              if(User.userExists(User(loginData._1, loginData._2))) {
                loggedUser = Some(U(loginData._1, loginData._2))
                Redirect(routes.Application.home())
              } else {
                Redirect(routes.Application.login())
              }
            }
          )
  }

  def home = Action {
    Ok(views.html.home())
  }

//  val taskForm = Form("label" -> nonEmptyText)
//
//
//  def index = Action {
//    Redirect(routes.Application.tasks())
//  }
//
//  def tasks = Action {
//    Ok(views.html.index(Task.all(), taskForm))
//  }
//
//  def newTask = Action { implicit request =>
//    println("smt")
//    taskForm.bindFromRequest.fold(
//      errors => BadRequest(views.html.index(Task.all(), errors)),
//      label => {
//        Task.create(label)
//        Redirect(routes.Application.tasks())
//      }
//    )
//  }
//
//  def deleteTask(id: Long) = Action {
//    Task.delete(id)
//    Redirect(routes.Application.tasks)
//  }


}