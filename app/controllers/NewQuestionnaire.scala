package controllers

import com.scalaq.survey.model.questionnaire.Questionnaire
import database.PersistanceAdapter
import models.{PresentationQuestion, PresentationQuestionnaire, Specs}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

class NewQuestionnaire extends Controller with Secured  {

  val questionsForm: Form[PresentationQuestionnaire] = Form(
    mapping(
      "body" -> default(nonEmptyText, ""),
      "description" -> default(nonEmptyText, ""),
      "questions" -> list(
        mapping(
          "tip" -> default(nonEmptyText, ""),
          "body" -> default(nonEmptyText, ""),
          "description" -> default(nonEmptyText, ""),
          "spec" -> mapping(
            "s1" -> list(text),
            "s2" -> list(text)
          )(Specs.apply)(Specs.unapply)
        )(PresentationQuestion.apply)(PresentationQuestion.unapply)
      )
    )(PresentationQuestionnaire.apply)(PresentationQuestionnaire.unapply)
  )

  def test = IsAuthenticated { username => implicit request =>
    Ok(views.html.newq(questionsForm))
  }

  def submit = Action { implicit request =>

    questionsForm.bindFromRequest.fold(
      errors => {
        println("ERROR")
        Ok(views.html.newq(questionsForm))
      },

      data => {
        val email = request.session.get("email").get

        val questionnaireBody = data.body
        val questionnaireDescription = if(data.description.length == 0) None else Some(data.description)
        val questions = for(q <- data.questions) yield q.getQuestion()

        val questionnaire = Questionnaire(questionnaireBody,questionnaireDescription,questions)

        PersistanceAdapter.saveQuestionnaire(email, questionnaire)
        Redirect(routes.Home.index())
      }
    )
  }
}
