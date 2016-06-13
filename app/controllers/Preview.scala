package controllers

import database.PersistanceAdapter
import models.{PQWrapper}
import play.api.mvc._


class Preview extends Controller with Secured {

  def index(body: String, description: String) = IsAuthenticated { username => implicit request =>
    val email = request.session.get("email").get

    var d = description
    if(description == " ") {
      d = ""
    }

    val persistenceQuestionnaire = PersistanceAdapter.getQuestionnaire(email, body, d)

    if(persistenceQuestionnaire == None) {
      Ok("Error")
    } else {
      val q = PQWrapper.getQuestionnaire(persistenceQuestionnaire.get)
      println(q.questions)
      Ok(views.html.preview(q))
    }
  }
}
