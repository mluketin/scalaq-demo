package models

import com.scalaq.survey.model.question._
import com.scalaq.survey.model.questionnaire.Questionnaire

import scalaq.persistence
import scala.collection.JavaConverters._


case class PQWrapper(q: Questionnaire)

object PQWrapper {
  def getQuestionnaire(persistenceQuestionnaire: persistence.Questionnaire): Questionnaire = {
    val name = persistenceQuestionnaire.getName
    val description = if (persistenceQuestionnaire.getDescription.length == 0) None else Some(persistenceQuestionnaire.getDescription)

    var listQuestions: Seq[Question] = Seq()
    for (question <- persistenceQuestionnaire.getQuestions.asScala) {

      if (question.getSpec.isInstanceOf[persistence.TextInputQuestion]) {
        val q = TextInputQuestion(question.getBody,
          if (question.getDescription.length == 0) None else Some(question.getDescription))

        listQuestions = listQuestions :+ q
      }

      if (question.getSpec.isInstanceOf[persistence.OrdinalScaleQuestion]) {

        println("PITANJE JE ORDINAL")

        val spec = question.getSpec.asInstanceOf[persistence.OrdinalScaleQuestion]

        val q = OrdinalScaleQuestion(
          question.getBody,
          if (question.getDescription.length == 0) None else Some(question.getDescription),
          spec.getMin,
          spec.getMax,
          if (spec.getMinLabel.length == 0) None else Some(spec.getMinLabel),
          if (spec.getMaxLabel.length == 0) None else Some(spec.getMaxLabel))

        listQuestions = listQuestions :+ q
      }


      if (question.getSpec.isInstanceOf[persistence.SingleSelectQuestion]) {

        val spec = question.getSpec.asInstanceOf[persistence.SingleSelectQuestion]


        val q = SingleSelectQuestion(question.getBody,
          if (question.getDescription.length == 0) None else Some(question.getDescription),
          spec.getOfferedAnswers.asScala,
          if(spec.getOther.length == 0) None else Some(spec.getOther)
        )

        listQuestions = listQuestions :+ q
      }

      if (question.getSpec.isInstanceOf[persistence.MultiSelectQuestion]) {

        val spec = question.getSpec.asInstanceOf[persistence.MultiSelectQuestion]


        val q = MultiSelectQuestion(question.getBody,
          if (question.getDescription.length == 0) None else Some(question.getDescription),
          spec.getOfferedAnswers.asScala,
          if(spec.getOther.length == 0) None else Some(spec.getOther)
        )

        listQuestions = listQuestions :+ q
      }

      if (question.getSpec.isInstanceOf[persistence.MatrixQuestion]) {

        val spec = question.getSpec.asInstanceOf[persistence.MatrixQuestion]
        val q = MatrixQuestion(question.getBody,
          if (question.getDescription.length == 0) None else Some(question.getDescription),
          spec.getRows.asScala,
          spec.getColumns.asScala
        )
        listQuestions = listQuestions :+ q
      }
    }

    Questionnaire(name,description,listQuestions)
  }
}
