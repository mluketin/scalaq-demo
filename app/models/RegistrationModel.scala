package models

import database.PersistanceAdapter

case class RegistrationModel(email: String, pass: String, repass: String)

object RegistrationModel {

  def emailInUse(email: String, pass: String, repass: String): Option[RegistrationModel] = {
    if (PersistanceAdapter.userExists(email)) {
      None
    } else {
      Some(RegistrationModel(email, pass, repass))
    }
  }

  def registerUser(registrationData: (String, String, String)): Boolean = {
    val md = java.security.MessageDigest.getInstance("SHA-1")
    val hash = md.digest(registrationData._2.getBytes("UTF-8"))
    PersistanceAdapter.registerUser(registrationData._1, hash)
  }
}
