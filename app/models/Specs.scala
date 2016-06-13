package models

import com.scalaq.survey.model.question.{MatrixQuestion, _}


case class Specs(s1: List[String], s2: List[String]) {

  def getTextInputQuestion(body: String, description: Option[String]): Question = {
    TextInputQuestion(body, description)
  }

  def getSingleSelectQuestion(body: String, description: Option[String]): Question = {
    SingleSelectQuestion(
      body,
      description,
      s1,
      if(s2.size == 0) None else Some(s2(0)))
  }

  def getMultiSelectQuestion(body: String, description: Option[String]): Question = {
    MultiSelectQuestion(
      body,
      description,
      s1,
      if(s2.size == 0) None else Some(s2(0)))
  }

  def getOrdinalScaleQuestion(body: String, description: Option[String]): Question = {
      OrdinalScaleQuestion(
      body,
      description,
      s1(0).toInt,
      s1(1).toInt,
      if(s1(2).length == 0) None else Some(s1(2)),
      if(s1(3).length == 0) None else Some(s1(3))
    )
  }

  def getMatrixQuestion(body: String, description: Option[String]): Question = {
    MatrixQuestion(
      body,
      description,
      s1,
      s2
    )
  }
}
