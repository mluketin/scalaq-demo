package models

import database.PersistanceAdapter

import scalaq.persistence.QOwnerEntity
import scalaq.persistence.User


case class HomeViewQuestionnaire(name: String, description: String, userId: Int, questId: Int, hash: Long, user: User, qOwner: QOwnerEntity) {

  def changeActiveState() : Unit = {
    PersistanceAdapter.changeActiveState(this)
  }
  

}
