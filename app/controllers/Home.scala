package controllers

import database.PersistanceAdapter
import play.api.mvc._

import scala.collection.JavaConverters._
import scalaq.persistence.QOwnerEntity


class Home extends Controller with Secured {

  def index = IsAuthenticated { username =>
    implicit request =>
    val email = request.session.get("email").get
    val questionnaires = PersistanceAdapter.getAllQuestionnaires(email)
      val url = routes.Home.index().absoluteURL()
    Ok(views.html.home(url, questionnaires))
  }

  def createNewQuestionnaire = Action { implicit request =>
    Redirect(routes.NewQuestionnaire.test())
  }

  def switch(userId: Int, questId: Int) = IsAuthenticated {  username =>
    implicit request =>

    val userOption = PersistanceAdapter.getUser(userId)

    if(userOption.isDefined) {

      val user = userOption.get
      for(owner <- user.getQOwners.asScala) {
        if(owner.getQuestionnaireID == questId) {
          PersistanceAdapter.changeActiveState(user, owner);
        }
      }

    }


    println("asd")

//    val email = request.session.get("email").get
//    val questionnaires = PersistanceAdapter.getAllQuestionnaires(email)
//    val url = routes.Home.index().absoluteURL()
    Redirect(routes.Home.index())
//    Ok(views.html.home(url, questionnaires))
  }

  def delete(userId: Int, questId: Int) = IsAuthenticated {  username =>
    implicit request =>

      val userOption = PersistanceAdapter.getUser(userId)

      if(userOption.isDefined) {

        var qOwner: QOwnerEntity = null

        val user = userOption.get
        for(owner <- user.getQOwners.asScala) {
          if(owner.getQuestionnaireID == questId) {
            qOwner = owner


          }
        }
        PersistanceAdapter.deleteQuestionnaire(user, qOwner)

      }


      println("asd")

      //    val email = request.session.get("email").get
      //    val questionnaires = PersistanceAdapter.getAllQuestionnaires(email)
      //    val url = routes.Home.index().absoluteURL()
      Redirect(routes.Home.index())
    //    Ok(views.html.home(url, questionnaires))
  }
}
