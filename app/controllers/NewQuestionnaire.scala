package controllers

import play.api.mvc.Controller
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._

case class QuestionType(text: String)

case class Question(body: String, description: String)

case class Questions(questions: List[Question])

class NewQuestionnaire extends Controller {

  var questionList: List[Question] = List()
  var first = true

//  val questionsForm: Form[Questions] = Form(
//    mapping(
//      "questions" -> seq(
//          mapping(
//            "body" -> nonEmptyText,
//            "description" -> nonEmptyText
//          ) (Question.apply)(Question.unapply)
//      )
//    )(Questions.apply)(Questions.unapply)
//  )

  val questionsForm: Form[Questions] = Form(
    mapping(
      "questions" -> list(
        mapping(
                      "body" -> default(nonEmptyText, ""),
                      "description" -> default(nonEmptyText, "")
        ) (Question.apply)(Question.unapply)
      )
    )(Questions.apply)(Questions.unapply)
  )

  val questionForm: Form[Question] = Form(
    mapping(
      "body" -> nonEmptyText,
      "description" -> nonEmptyText
    ) (Question.apply)(Question.unapply)
  )

  val filled = questionsForm.fill(Questions(List(
    Question("prvo", "prvi desc"),
    Question("drugo", "drugi")
  )))


  val types = Seq(QuestionType("Text Input"),
    QuestionType("Single Select"),
    QuestionType("Multi select"),
    QuestionType("Ordinal Scale"),
    QuestionType("Matrix Question"))


//  val questionTypeForm = Form(
//    "Choose Type" -> nonEmptyText
//  )



  def index = Action{
//    Ok(views.html.newQ(types, questionsForm))//, questionList))
    Ok(views.html.newQ(types, questionsForm))//, questionList))
  }

  //pogeldat sta je upisano i vratit view sa dodanim stvarima
  def addQuestion = Action { implicit request =>
//    val newPart = (Question("","")) :: Nil
    //    val newFilled = questionsForm.fill(Questions(questionList))
//    questionList = questionList ::: newPart
//
//    Ok(views.html.newQ(types, questionsForm))//, questionList))
    questionsForm.bindFromRequest.fold(
      errors => {
        println("ERROR")
        Ok(views.html.newQ(types, questionsForm))//, questionList))
      },

      data => {
//        if(!first) {
          val newPart = (Question("","")) :: Nil
          questionList = data.questions ::: newPart
          println("NEW PART SIZE " + newPart.size)
          println("DATA SIZE " + data.questions.size)
          println("Q LIST SIZE " + questionList.size)

//        }
//        else {
//          first = false
//        }


//        val newList: List[Question] = data.questions ::: newPart
        val newFilled = questionsForm.fill(Questions(questionList))
        Ok(views.html.newQ(types, newFilled))//, questionList))
      }
    )


  }

//  def printQ = Action { implicit request =>
//
//      questionsForm.bindFromRequest.fold(
//        errors => {
//          println("ERROR")
//          Ok(views.html.newQ(types, questionsForm))//, questionList))
//        },
//
//        data => {
//          println("SIZE: " + data.questions.size)
////          val newPart = (Question("","")) :: Nil
////          val newList: List[Question] = data.questions ::: newPart
//
////          val newFilled = questionsForm.fill(Questions(newList))
//
//          for(q <- data.questions) {
//            println(q.body)
//            println(q.description)
//          }
////          val newFilled = questionsForm.fill(Questions(data.questions))
//
//          Ok(views.html.newQ(types, questionsForm))//, questionList))
//        }
//      )
//
////    Ok(views.html.newQ(types, questionsForm))//, questionList))
//  }

}
