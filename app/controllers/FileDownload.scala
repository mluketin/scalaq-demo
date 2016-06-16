package controllers

import java.io.File

import com.scalaq.survey.export.Mapper
import database.{JavaToScalaConverter, PersistanceAdapter}
import play.api.mvc._

class FileDownload extends Controller with Secured{

  def downloadFile = Action { implicit request =>
    Ok.sendFile(new File("""D:/export.xlsx"""), inline=true).withHeaders(CACHE_CONTROL->"max-age=3600",CONTENT_DISPOSITION->"attachment; filename=export.xlsx", CONTENT_TYPE->"application/x-download")
  }

  def downloadDocx(userId: Int, questId: Int) = IsAuthenticated { username =>
    implicit request =>

      val optionUser = PersistanceAdapter.getUser(userId)
      val email = request.session.get("email")

      val mapper = mapResults(userId, questId, email.get)

     if(mapper != null) {
       val persQuestOpt = PersistanceAdapter.getQuestionnaire(userId, questId)

       val in = FileDownload.this.getClass.getResourceAsStream("/Template.docx")

       val file = mapper.exportDocx(in)
       val fileName = persQuestOpt.get.getName.replace(" ", "")
       val string = "attachment; filename=" + "Report" +".docx"

       Ok.sendFile(file, inline=true).withHeaders(CACHE_CONTROL->"max-age=3600",CONTENT_DISPOSITION->string, CONTENT_TYPE->"application/x-download")
     } else {
       BadRequest("User does not exist")
     }
  }

  def downloadXlsx(userId: Int, questId: Int) = IsAuthenticated { username =>
    implicit request =>

      val optionUser = PersistanceAdapter.getUser(userId)
      val email = request.session.get("email")

      val mapper = mapResults(userId, questId, email.get)

      if(mapper != null) {
        val persQuestOpt = PersistanceAdapter.getQuestionnaire(userId, questId)

        val in = FileDownload.this.getClass.getResourceAsStream("/Template.xlsx")

        val file = mapper.exportXlsx(in)
        val fileName = persQuestOpt.get.getName.replace(" ", "")
        println(fileName)
        val string = "attachment; filename=" + "Report" +".xlsx"

        Ok.sendFile(file, inline=true).withHeaders(CACHE_CONTROL->"max-age=3600",CONTENT_DISPOSITION->string, CONTENT_TYPE->"application/x-download")
      } else {
        BadRequest("User does not exist")
      }
  }

  def mapResults(userId: Int, questId: Int, email: String): Mapper = {

    println("CONTROLLER MAPPER")

    val optionUser = PersistanceAdapter.getUser(userId)

    println(optionUser)
    println(email)

    if (optionUser.isDefined && optionUser.get.getEmail == email) {
      val persQuestOpt = PersistanceAdapter.getQuestionnaire(userId, questId)
      if (persQuestOpt.isDefined) {

        val modelQuest = JavaToScalaConverter.getModelQuestionnaire(persQuestOpt.get)
        println(modelQuest)

        val mapper = new Mapper(modelQuest)

        val completedQuestionnaires = PersistanceAdapter.getCompletedModelQuestionnaires(optionUser.get, persQuestOpt.get, modelQuest)
        mapper.mapResults(completedQuestionnaires)

        return mapper
      }
    }
    null
  }
}