package controllers

import java.io.File

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._

class FileDownloadController extends Controller{

  def index = Action {
    Ok(views.html.downloadFile())
  }

  def downloadFile = Action {
    Ok.sendFile(new File("""E:\json example.txt"""), inline=true).withHeaders(CACHE_CONTROL->"max-age=3600",CONTENT_DISPOSITION->"attachment; filename=smth.txt", CONTENT_TYPE->"application/x-download")
//    Ok(views.html.allQ())
  }
}