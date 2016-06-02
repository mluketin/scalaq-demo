module persistence
{
	aggregate User {
	  String  email;
    String  password;
    List<QOwnerEntity> qOwners;
    List<CQOwnerEntitiy> cqOwners;
  }
  
  entity QOwnerEntity {
    Questionnaire *questionnaire;
  }
  
  entity CQOwnerEntitiy {
    CompletedQuestionnaire *completedQuestionnaire;
  }
}
