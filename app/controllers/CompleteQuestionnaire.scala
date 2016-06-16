package controllers

import com.scalaq.survey.model.answer._
import com.scalaq.survey.model.questionnaire.{CompletedQuestionnaire, Questionnaire}
import database.{JavaToScalaConverter, PersistanceAdapter}
import models.PQWrapper
import play.api.data.Form
import play.api.mvc.{Action, Controller}

import scala.collection.JavaConverters._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.twirl.api.TemplateMagic.anyToDefault

class CompleteQuestionnaire extends Controller {
  val forma = Form(
    tuple(
      "user" -> text,
      "quest" -> text,
      "elements" -> list(
        tuple(
          "tip" -> text,
          "answer" -> text,
          "option" -> text
        )
      )
    )

  )

  def index(userId: Int, questId: Int, hash: Long) = Action { implicit request =>

    val user = PersistanceAdapter.getUser(userId)

    if (!user.isDefined) {
      Ok("Error, questionnaire does not exist.")
    } else {


      val qOwnerSeq = for (o <- user.get.getQOwners.asScala
                           if (o.getQuestionnaireID == questId && o.getHash == hash)) yield o

      if (qOwnerSeq.size < 1) {
        Ok("Error, questionnaire does not exist.")
      } else {


        val qOwner = qOwnerSeq(0)

        if (!qOwner.getActive) {
          Ok("Error, questionnaire is not open for completing.")
        } else {


          val questionnaire = qOwner.getQuestionnaire


          val q = PQWrapper.getQuestionnaire(questionnaire)
//          println(form, q.questions)
//          val anyData = Map("user" -> userId.toString, "quest" -> questId.toString, "elements" -> List[(String, String, String)])
//          val (user, quest, elements) = forma.bind(anyData).get

          Ok(views.html.fill(forma, q, userId, questId))
        }
      }
    }
  }


  private def getQ(userId: Int, questId: Int): Option[Questionnaire] = {
    val user = PersistanceAdapter.getUser(userId)
    if (!user.isDefined) {
      return None
    } else {
      val qOwnerSeq = for (o <- user.get.getQOwners.asScala
                           if (o.getQuestionnaireID == questId)) yield o
      if (qOwnerSeq.size < 1) {
        None
      } else {
        val qOwner = qOwnerSeq(0)

        if (!qOwner.getActive) {
          None
        } else {
          val questionnaire = qOwner.getQuestionnaire
          val q = PQWrapper.getQuestionnaire(questionnaire)
          return Some(q)
        }
      }
    }
  }



  def complete = Action { implicit request =>
    forma.bindFromRequest.fold(
      formWithErrors =>  BadRequest("Error"),
      data => {
        val user = data._1
        val quest = data._2
        val elements = data._3
        val answerArray = new Array[Answer](elements.size)

        var counter = 0
        for(item <- elements) {

          if(item._1 == "T") {

            val answer = if(item._2.length > 0) TextAnswer(item._2) else Unanswered()
            answerArray(counter) = answer

          } else if (item._1 == "S") {

            if(item._2.length > 0) {
              answerArray(counter) = SingleSelectAnswer(Some(item._2.toInt), None)
            } else if (item._3.length > 0) {
              answerArray(counter) = SingleSelectAnswer(Some(item._3.toInt), None)
            } else {
              answerArray(counter) = Unanswered()
            }


          } else if (item._1 == "M") {

            val other: Option[String] = if(item._3.length > 0) Some(item._3) else None

            if(item._2.length > 0) {

              val answer = item._2.split("_")

              scala.util.Sorting.quickSort(answer)

              val a = for(ans <- answer) yield ans.toInt

              answerArray(counter) = MultiSelectAnswer(Some(a), other)

            } else {
              answerArray(counter) = MultiSelectAnswer(None, other)
            }



          } else if (item._1 == "O") {

            val answer = if(item._2.length > 0) OrdinalScaleAnswer(item._2.toInt) else Unanswered()
            answerArray(counter) = answer


          } else if (item._1 == "X") {
            if(item._2.length > 0) {
              val answer = item._2.substring(1, item._2.length-1).replace(")(", " ").split(" ")
              println("MATRIX " + answer)

              val a = for(ans <- answer) yield ans.toInt

              answerArray(counter) = MatrixAnswer(a)
            }
          }

          counter += 1
        }
        PersistanceAdapter.saveCompletedQuestionnaire(user.toInt, quest.toInt, answerArray)

        Redirect(routes.Auth.login())
      }
    )
  }




}
