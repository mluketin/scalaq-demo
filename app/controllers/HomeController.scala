package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._

case class Quest(body: String, description: String)


class HomeController extends Controller{


  val quests = Seq(
    Quest("Prvi q", "neki opis"),
    Quest("Drugi q", "neki opis"),
    Quest("Treci q", "neki opis"),
    Quest("Cetvrti q", "neki opis")
  )

  def showQuestionnairesButton = Action {
    Ok(views.html.allQ(quests))
  }

  def createNewQuestionnaire = Action {
    Redirect(routes.NewQuestionnaire.index())
  }


}
