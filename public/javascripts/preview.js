function getRadioButtonName() {
    var questionsNumber = document.getElementById('questions').childElementCount
    return 'group'.concat(questionsNumber.toString())
}