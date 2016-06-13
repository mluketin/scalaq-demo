function newSQuestion() {
    var qNumber = getQuestionsCount();
    var qNumberString = qNumber.toString();

    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = divTip.innerHTML = newTextInputWithValue('questions['.concat(qNumberString,'].tip'), 'S');

    var divBody = document.createElement('div');
    divBody.innerHTML = newRequiredTextInput('questions['.concat(qNumberString,'].body'), 'Question body');

    var divDescription = document.createElement('div');
    divDescription.innerHTML = newTextInput('questions['.concat(qNumberString,'].description'), 'Question description');

    var specDiv = document.createElement('div');
    specDiv.class = 'spec';



    var qDiv = document.createElement('div');
    qDiv.className = 'question-class';

    var divRow = document.createElement('div');
    divRow.class = 'row';

    var divLeft = document.createElement('div');
    divLeft.class = 'col-md-6';

    var divRight = document.createElement('div');
    divRight.class = 'col-md-6';

    qDiv.appendChild(divRow);
    divRow.appendChild(divLeft);
    divRow.appendChild(divRight);

    divLeft.appendChild(divTip);
    divLeft.appendChild(divBody);
    divLeft.appendChild(divDescription);
    divLeft.appendChild(specDiv);



    var button = document.createElement('input');
    button.type = 'button';
    button.value = 'Add Option';
    button.onclick = function(){addOption(qNumberString, specDiv)};
    divRight.appendChild(button);



    document.getElementById('qcontainer').appendChild(qDiv);
}