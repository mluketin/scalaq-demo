module persistence
{
  aggregate User {
    String email { unique; }
    binary hash;
    List<QOwnerEntity> qOwners;
    List<CQOwnerEntity> cqOwners;
    
    specification hasEmail
          'it => it.email == email'
          {
              String email;
          }
  }
  
  entity QOwnerEntity {
    Questionnaire *questionnaire;
    boolean active;
    long hash;
  }
  
  entity CQOwnerEntity {
    CompletedQuestionnaire *completedQuestionnaire;
  }
}
