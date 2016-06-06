module persistence
{
  aggregate User {
    String  email { unique; }
    binary hash;
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
