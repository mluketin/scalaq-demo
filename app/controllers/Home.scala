package controllers

import database.PersistanceAdapter
import play.api.mvc._

class Home extends Controller with Secured {

  def index = IsAuthenticated { username =>
    implicit request =>
    val email = request.session.get("email").get
    val questionnaires = PersistanceAdapter.getAllQuestionnaires(email)
    Ok(views.html.home(questionnaires))
  }

  def createNewQuestionnaire = Action { implicit request =>
    Redirect(routes.NewQuestionnaire.test())
  }
}
