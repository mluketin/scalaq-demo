package database

import com.scalaq.survey.database.DbAdapter
import com.scalaq.survey.model.questionnaire.Questionnaire
import models.{PresentationQuestionnaire, HomeViewQuestionnaire}

import scalaq._
import scalaq.persistence._
import scalaq.persistence.repositories.UserRepository
import scala.collection.JavaConverters._


object PersistanceAdapter {

  private val locator = Boot.configure("jdbc:postgresql://localhost:5432/scalaq?user=scalaq&password=scalaq")

  private val usersRepository = locator.resolve(classOf[UserRepository])

  def registerUser(email: String, hash: Array[Byte]): Boolean = {
    val user: User = new User().setEmail(email).setHash(hash)
    usersRepository.insert(user)
    true
  }

  def userExists(email: String, hash: Array[Byte]): Boolean = {
    val hasEmail = new User.hasEmail(email)
    val userList = usersRepository.search(hasEmail)
    for(user: User <- userList.asScala) {
        if(java.util.Arrays.equals(user.getHash, hash))
          return true
    }
    false
  }

  def userExists(email: String): Boolean = {
    val hasEmail = new User.hasEmail(email)
    val userList = usersRepository.search(hasEmail)
    if (userList.size() > 0) true else false
  }

  def getUser(email: String): Option[User] = {
    val hasEmail = new User.hasEmail(email)
    val userList = usersRepository.search(hasEmail)
    if(userList.size() > 0) Some(userList.get(0)) else None
  }

  def saveQuestionnaire(userEmail: String, questionnaire: Questionnaire): Unit = {
    val optionUser = getUser(userEmail)

    if(optionUser != None) {
      val user = optionUser.get

      val questionnaireList = user.getQOwners
      val questionnairePersistenceObject: persistence.Questionnaire = DbAdapter.saveQuestionnaire(questionnaire)
      questionnaireList.add(new QOwnerEntity(questionnairePersistenceObject))
      usersRepository.update(user)
    }
  }

  def getAllQuestionnaires(userEmail: String): Seq[HomeViewQuestionnaire] = {
    val optionUser = getUser(userEmail)

    if(optionUser != None) {
      return for(qOwner <- optionUser.get.getQOwners.asScala)
        yield HomeViewQuestionnaire(
          qOwner.getQuestionnaire.getName,
          qOwner.getQuestionnaire.getDescription)
    }
    Seq() //TODO
  }

  def deleteQuestionnaire(userEmail: String, questionnaire: HomeViewQuestionnaire): Unit = {
    val optionUser = getUser(userEmail)

    if(optionUser != None) {

      val user = optionUser.get

      val questionnaireList = user.getQOwners

      for(qOwner <- optionUser.get.getQOwners.asScala) {
        if(qOwner.getQuestionnaire.getName == questionnaire.name && qOwner.getQuestionnaire.getDescription == questionnaire.description) {
          questionnaireList.remove(qOwner)
          DbAdapter.deleteQuestionnaire(qOwner.getQuestionnaire)
        }
      }
      usersRepository.update(user)
    }

  }

  def getQuestionnaire(userEmail: String, body: String, description: String): Option[persistence.Questionnaire] = {
    val optionUser = getUser(userEmail)

    if(optionUser != None) {

      val user = optionUser.get

      val questionnaireList = user.getQOwners

      for(qOwner <- optionUser.get.getQOwners.asScala) {
        if(qOwner.getQuestionnaire.getName == body && qOwner.getQuestionnaire.getDescription == description) {
          return Some(qOwner.getQuestionnaire)
        }
      }
    }
    None
  }

}
