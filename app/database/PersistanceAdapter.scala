package database

import com.scalaq.survey.database.DbAdapter
import com.scalaq.survey.model.questionnaire.Questionnaire
import models.{HashGenerator, HomeViewQuestionnaire, PresentationQuestionnaire}

import com.scalaq.survey.model.answer
import com.scalaq.survey.model.questionnaire

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

  def getUser(id: Int): Option[User] = {
    for(u <- usersRepository.search().asScala) {
      if(u.getID == id)
          return Some(u)
    }
    None
  }

  def getUserId(email: String): Option[Int] = {
    val hasEmail = new User.hasEmail(email)
    val userList = usersRepository.search(hasEmail)
    if(userList.size() > 0) Some(userList.get(0).getID) else None
  }

  def saveQuestionnaire(userEmail: String, questionnaire: Questionnaire): Unit = {
    val optionUser = getUser(userEmail)

    if(optionUser.isDefined) {
      val user = optionUser.get

      val questionnaireList = user.getQOwners
      val questionnairePersistenceObject = DbAdapter.saveQuestionnaire(questionnaire)
      val quest = questionnairePersistenceObject.get

      questionnaireList.add(
        new QOwnerEntity()
          .setQuestionnaire(quest)
          .setActive(false)
          .setHash(HashGenerator.getCrc32(quest.getName + quest.getDescription)))
      usersRepository.update(user)
    }
  }

  def getAllQuestionnaires(userEmail: String): Seq[HomeViewQuestionnaire] = {
    val optionUser = getUser(userEmail)

    if(optionUser != None) {
      return for(qOwner <- optionUser.get.getQOwners.asScala)
        yield HomeViewQuestionnaire(
          qOwner.getQuestionnaire.getName,
          qOwner.getQuestionnaire.getDescription,
          optionUser.get.getID,
          qOwner.getQuestionnaire.getID,
          qOwner.getHash,
          optionUser.get,
          qOwner)

    }
    Seq()
  }

  def deleteQuestionnaire(user: User, ownerEntity: QOwnerEntity): Unit = {
      val q = ownerEntity.getQuestionnaire
      user.getQOwners.remove(ownerEntity)
      usersRepository.update(user)
//     DbAdapter.deleteQuestionnaire(q);
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

  def getQuestionnaire(userId: Int, questId: Int, hash: Long): Option[persistence.Questionnaire] = {
      val user = getUser(userId)
      for(qOwner <- user.get.getQOwners.asScala) {
        if(qOwner.getQuestionnaireID == questId && qOwner.getHash == hash) {
          return Some(qOwner.getQuestionnaire)
        }
      }
    None
  }

  def getQuestionnaire(user: User, questId: Int): Option[persistence.Questionnaire] = {
    for(qOwner <- user.getQOwners.asScala) {
      if(qOwner.getQuestionnaireID == questId) {
        return Some(qOwner.getQuestionnaire)
      }
    }
    None
  }

  def getQuestionnaire(userId: Int, questId: Int): Option[persistence.Questionnaire] = {
    val user = getUser(userId)
    for(qOwner <- user.get.getQOwners.asScala) {
      if(qOwner.getQuestionnaireID == questId) {
        return Some(qOwner.getQuestionnaire)
      }
    }
    None
  }

  def changeActiveState(user: User, qOwner: QOwnerEntity): Unit = {
    qOwner.setActive(!qOwner.getActive)
    usersRepository.update(user)
  }

  def changeActiveState(homeViewQuest: HomeViewQuestionnaire): Unit = {
    val user = homeViewQuest.user
    val qOwner = homeViewQuest.qOwner
    qOwner.setActive(!qOwner.getActive)
    usersRepository.update(user)
  }

  def saveCompletedQuestionnaire(userId: Int, questId: Int, answers: Seq[answer.Answer]): Unit = {
    val user = getUser(userId)
    val quest = getQuestionnaire(user.get, questId).get

    val completedQuestionnaire: persistence.CompletedQuestionnaire = questionnaire.CompletedQuestionnaire.getPersistenceCompleteQuestionnaire(quest, answers)

    DbAdapter.saveCompletedQuestionnaire(completedQuestionnaire)

    val cQOwnerEntity = new CQOwnerEntity().setCompletedQuestionnaire(completedQuestionnaire)
    user.get.getCqOwners.add(cQOwnerEntity)

    usersRepository.update(user.get)
  }

  def getCompletedModelQuestionnaires(user: User, persistQuest: persistence.Questionnaire, modelQuest:questionnaire.Questionnaire): Seq[questionnaire.CompletedQuestionnaire] = {

    val cqList = user.getCqOwners
    val qId = persistQuest.getID

    for(owner <- cqList.asScala
      if (owner.getCompletedQuestionnaire.getQuestionnaireID == qId) )
      yield JavaToScalaConverter.getModelCompleteQuestinaire(owner.getCompletedQuestionnaire, modelQuest)
  }



}
