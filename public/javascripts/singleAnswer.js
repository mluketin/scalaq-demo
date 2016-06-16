function newSingleAnswerFirstPart(questionNumber) {
    var id = 'question' + questionNumber.toString();

    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].tip'), 'S');


    var divField = document.createElement('div');
    divField.hidden = true;
    divField.innerHTML = newTextInputAnswer('elements['.concat(questionNumber,'].answer'));

    var divOption = document.createElement('div');
    divOption.hidden = true;
    divOption.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].option'), '');

    var containerDiv = document.getElementById(id);
    containerDiv.appendChild(divTip);
    containerDiv.appendChild(divField);
    // containerDiv.appendChild(divOption);
}

function newSingleAnswerOtherPart(questionNumber) {
    var id = 'question' + questionNumber.toString();

    var divOption = document.createElement('div');
    divOption.hidden = true;
    divOption.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].option'), '');

    var containerDiv = document.getElementById(id);
    containerDiv.appendChild(divOption);
}

function setSingleOfferedAnswer(answer, questionNumber) {

    console.log(answer)

    var answerName = 'elements['.concat(questionNumber,'].answer');
    var optionName = 'elements['.concat(questionNumber,'].option');

    var answerBox = document.getElementsByName(answerName).item(0);
    answerBox.value = answer.toString();

    var optionBox = document.getElementsByName(optionName).item(0);
    optionBox.value = '';
}

function setSingleOtherAnswer(questionNumber) {
    var answerName = 'elements['.concat(questionNumber,'].answer');
    var optionName = 'elements['.concat(questionNumber,'].option');


    var answerBox = document.getElementsByName(answerName).item(0);
    answerBox.value = '';

    var optionBox = document.getElementsByName(optionName).item(0);
    optionBox.disabled = 'false';
}

function clickInput(input) {
    input.checked = 'true';
    input.click();
}

function generateSingleRadioButton(answer, questionNumber, label) {

    var id = 'question' + questionNumber.toString();

    var namevalue = getRadioButtonName();


    var input = document.createElement('input');
    input.type = 'radio';
    input.name = 'group'.concat(questionNumber.toString());
    input.onclick = function(){setSingleOfferedAnswer(answer,questionNumber )};

    var containerDiv = document.getElementById(id);


    var div = document.createElement('div');

    var span1 = document.createElement('span');
    span1.appendChild(input);
    var span2 = document.createElement('span');
    span2.innerHTML = " " + label;
    div.appendChild(span1);
    div.appendChild(span2);

    containerDiv.appendChild(div)
}

function generateSingleOtherRadioBtn(questionNumber, label) {
    var id = 'question' + questionNumber.toString();

    var namevalue = getRadioButtonName();


    var div = document.createElement('div');

    var input = document.createElement('input');
    input.type = 'radio';
    input.name = 'group'.concat(questionNumber.toString());
        input.onclick = function(){setSingleOtherAnswer(questionNumber )};

    console.log(namevalue)

    var divOption = document.createElement('div');
    divOption.hidden = false;
    divOption.placeholder = label;
    divOption.innerHTML = newTextInputAnswer('elements['.concat(questionNumber,'].option'));


    div.appendChild(input);
    div.appendChild(divOption);

    var containerDiv = document.getElementById(id);
    containerDiv.addChild(div);
}