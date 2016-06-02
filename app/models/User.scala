package models

/**
  * Created by Marin on 21.5.2016..
  */
case class User(email:String, pass: String)

object User {

  val users = Seq(
    User("marin.luketin@gmail.com", "aaaa"),
    User("mirela.ostrek@gmail.com", "aaaa")
  )

  def userExists(user: User): Boolean = {
    users.contains(user)
  }

}
