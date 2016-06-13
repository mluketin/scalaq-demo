package models

object EmailValidator {

  val Email = """(\w+)@([\w\.]+)""".r

  def isValid(email: String): Boolean = {
//    val res = """(\w+)@([\w\.]+)""".r.unapplySeq(email.trim.reverse.trim.reverse).isDefined
//    val res =  email.matches(Email.toString())
    Email.findFirstIn(email).isDefined
  }
}
