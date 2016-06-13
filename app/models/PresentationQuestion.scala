package models

import com.scalaq.survey.model.question.Question


case class PresentationQuestion(tip: String, body: String, description: String, specs: Specs) {

  def getQuestion(): Question = {

    if (tip == "T") {
      specs.getTextInputQuestion(body,
        if (description.length == 0) None else Some(description))
    } else if (tip == "S") {
      specs.getSingleSelectQuestion(body,
        if (description.length == 0) None else Some(description))
    } else if (tip == "M") {
      specs.getMultiSelectQuestion(body,
        if (description.length == 0) None else Some(description))
    } else if (tip == "O") {
      specs.getOrdinalScaleQuestion(body,
        if (description.length == 0) None else Some(description))
    } else {
      specs.getMatrixQuestion(body,
        if (description.length == 0) None else Some(description))
    }
  }
}
