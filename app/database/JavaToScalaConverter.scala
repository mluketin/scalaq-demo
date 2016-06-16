package database

import com.scalaq.survey.model
import com.scalaq.survey.model.answer._
import com.scalaq.survey.model.question._
import com.scalaq.survey.model.questionnaire
import com.scalaq.survey.model.questionnaire.{CompletedQuestionnaire, Questionnaire}

import scala.collection.JavaConverters._
import scalaq.persistence

object JavaToScalaConverter {

  def getModelQuestionnaire(persQuest: persistence.Questionnaire): questionnaire.Questionnaire = {

    val name = persQuest.getName
    val description: Option[String] = if(persQuest.getDescription.length == 0) None else Some(persQuest.getDescription.length.toString)

    val questionArray = new Array[Question](persQuest.getQuestions.size())

    var counter = 0
    for(q <- persQuest.getQuestions.asScala) {

      val body = q.getBody
      val description: Option[String] = if(q.getDescription.length == 0) None else Some(q.getDescription.length.toString)


      if(q.getSpec.isInstanceOf[persistence.TextInputQuestion]) {
        questionArray(counter) = TextInputQuestion(body, description)

      } else if (q.getSpec.isInstanceOf[persistence.SingleSelectQuestion]) {
        val offeredAnswers = q.getSpec.asInstanceOf[persistence.SingleSelectQuestion].getOfferedAnswers.asScala.toSeq
        val other: Option[String] = if(q.getSpec.asInstanceOf[persistence.SingleSelectQuestion].getOther.length == 0) None else Some(q.getSpec.asInstanceOf[persistence.SingleSelectQuestion].getOther)

        questionArray(counter) = SingleSelectQuestion(body, description, offeredAnswers, other)

      } else if (q.getSpec.isInstanceOf[persistence.MultiSelectQuestion]) {
        val offeredAnswers = q.getSpec.asInstanceOf[persistence.MultiSelectQuestion].getOfferedAnswers.asScala.toSeq
        val other: Option[String] = if(q.getSpec.asInstanceOf[persistence.MultiSelectQuestion].getOther.length == 0) None else Some(q.getSpec.asInstanceOf[persistence.MultiSelectQuestion].getOther)

        questionArray(counter) = MultiSelectQuestion(body, description, offeredAnswers, other)

      } else if (q.getSpec.isInstanceOf[persistence.OrdinalScaleQuestion]) {

        val spec = q.getSpec.asInstanceOf[persistence.OrdinalScaleQuestion]

        val min = spec.getMin
        val max = spec.getMax
        val minLabel = if(spec.getMinLabel.length == 0) None else Some(spec.getMinLabel)
        val maxLabel = if(spec.getMaxLabel.length == 0) None else Some(spec.getMaxLabel)

        questionArray(counter) = OrdinalScaleQuestion(body, description, min, max, minLabel, maxLabel)

      } else if (q.getSpec.isInstanceOf[persistence.MatrixQuestion]) {
        val spec = q.getSpec.asInstanceOf[persistence.MatrixQuestion]

        val rows = spec.getRows.asScala
        val columns = spec.getColumns.asScala

        questionArray(counter) = MatrixQuestion(body, description, rows, columns)
      }
      counter += 1
    }
    Questionnaire(name, description, questionArray)
  }

  def getModelCompleteQuestinaire(persCq: persistence.CompletedQuestionnaire, modelQuest: Questionnaire) : questionnaire.CompletedQuestionnaire = {

    val answerArray = new Array[Answer](persCq.getAnswers.size())

    var counter = 0
    for(answer <- persCq.getAnswers.asScala ) {

      val spec = answer.getSpec

      if(spec.isInstanceOf[persistence.TextAnswer]) {
        answerArray(counter) = TextAnswer(spec.asInstanceOf[persistence.TextAnswer].getText)

      } else if(spec.isInstanceOf[persistence.SingleSelectAnswer]) {

        val singleSpec = spec.asInstanceOf[persistence.SingleSelectAnswer]

        val selected = if(singleSpec.getSelected == -1) None else Some(singleSpec.getSelected)
        val other = if(singleSpec.getOther.length == 0) None else Some(singleSpec.getOther)

        answerArray(counter) = SingleSelectAnswer(selected.asInstanceOf[Option[Integer]], other)

      } else if(spec.isInstanceOf[persistence.MultiSelectAnswer]) {

        val multiSpec = spec.asInstanceOf[persistence.MultiSelectAnswer]
        val other = if(multiSpec.getOther.length == 0) None else Some(multiSpec.getOther)

        if(multiSpec.getSelectedList.size() == 0) {
          answerArray(counter) = MultiSelectAnswer(None, other)

        } else {
          val list = multiSpec.getSelectedList.asScala
          val sequence: Seq[Int] = for(item <- list) yield item.asInstanceOf[Int]
          answerArray(counter) = MultiSelectAnswer(Some(sequence), other)

        }

      } else if(spec.isInstanceOf[persistence.OrdinalScaleAnswer]) {

        answerArray(counter) = OrdinalScaleAnswer(spec.asInstanceOf[persistence.OrdinalScaleAnswer].getSelected)

      } else if(spec.isInstanceOf[persistence.MatrixAnswer]) {

        val list = for(item <- spec.asInstanceOf[persistence.MatrixAnswer].getSelectedList.asScala) yield item.asInstanceOf[Int]

        answerArray(counter) = MatrixAnswer(list)

      } else if(spec.isInstanceOf[persistence.Unanswered]) {
        answerArray(counter) = Unanswered()

      }
        counter += 1
    }

    CompletedQuestionnaire(modelQuest, answerArray)
  }

}
