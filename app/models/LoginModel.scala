package models

import controllers.routes
import database.PersistanceAdapter

case class LoginModel(email: String, pass: String)

object LoginModel {

  def userExists(email: String, pass: String): Option[LoginModel] = {
    if(PersistanceAdapter.userExists(email, HashGenerator.getSha1(pass))) {
      Some(LoginModel(email, pass))
    } else {
      None
    }
  }

  def getUserId(email: String): Option[Int] = {
    PersistanceAdapter.getUserId(email)
  }
}
