package controllers

import java.io.File

import play.api.mvc._

class FileDownload extends Controller{

  def downloadFile = Action { implicit request =>
    Ok.sendFile(new File("""D:/export.xlsx"""), inline=true).withHeaders(CACHE_CONTROL->"max-age=3600",CONTENT_DISPOSITION->"attachment; filename=export.xlsx", CONTENT_TYPE->"application/x-download")
  }
}