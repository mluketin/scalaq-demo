function addRow(qNumberString, specDiv) {
    var answersCount = specDiv.childElementCount;
    var aNumberString = answersCount.toString();

    var div = document.createElement('div');

    div.innerHTML = newRequiredTextInput('questions['.concat(qNumberString,'].spec.s1[',answersCount,']'), 'Row '.concat(aNumberString));
    // div.innerHTML = '<dl id=questions_'.concat(qNumberString, '_spec_s1_', aNumberString ,'_field><dt><label for=questions_', qNumberString , '_spec_s1></label></dt><dd><input id=questions_', qNumberString ,'_spec_s1 class=login-class name=questions[', qNumberString,'].spec.s1[',answersCount,'] type=text></dd>');

    specDiv.appendChild(div)
}

function addColumn(qNumberString, specDiv) {
    var answersCount = specDiv.childElementCount;
    var aNumberString = answersCount.toString();

    var div = document.createElement('div');

    div.innerHTML = newRequiredTextInput('questions['.concat(qNumberString,'].spec.s2[',answersCount,']'), 'Column '.concat(aNumberString));
    // div.innerHTML = '<dl id=questions_'.concat(qNumberString, '_spec_s1_', aNumberString ,'_field><dt><label for=questions_', qNumberString , '_spec_s1></label></dt><dd><input id=questions_', qNumberString ,'_spec_s1 class=login-class name=questions[', qNumberString,'].spec.s1[',answersCount,'] type=text></dd>');

    specDiv.appendChild(div)
}


function newXQuestion() {
    var qNumber = getQuestionsCount();
    var qNumberString = qNumber.toString();

    var qDiv = document.createElement('div');
    qDiv.className = 'question-class';

    var leftDiv = document.createElement('div');
    leftDiv.className = 'left-div';
    leftDiv.style.position = 'left';


    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = newTextInputWithValue('questions['.concat(qNumberString,'].tip'), 'X');
    leftDiv.appendChild(divTip);

    var divBody = document.createElement('div');
    divBody.innerHTML = newRequiredTextInput('questions['.concat(qNumberString,'].body'), 'Question body');
    leftDiv.appendChild(divBody);

    var divDescription = document.createElement('div');
    divDescription.innerHTML = newTextInput('questions['.concat(qNumberString,'].description'), 'Question description');
    leftDiv.appendChild(divDescription);

    var specDiv = document.createElement('div');
    specDiv.id = 'spec'.concat(qNumberString);
    leftDiv.appendChild(specDiv);

    var specDiv2 = document.createElement('div');
    specDiv2.id = 'spec'.concat(qNumberString);
    leftDiv.appendChild(specDiv2);

    qDiv.appendChild(leftDiv);

    var rightDiv = document.createElement('div');
    rightDiv.className = 'right-div';
    rightDiv.style.position = 'right';

    qDiv.appendChild(rightDiv);

    var bottomDiv = document.createElement('div');
    bottomDiv.className = 'bottom-div';
    bottomDiv.style.position = 'right';
    qDiv.appendChild(bottomDiv);

    var button2 = document.createElement('input');
    button2.type = 'button';
    button2.value = 'Add Column';
    button2.onclick = function(){addColumn(qNumberString, specDiv2)};
    bottomDiv.appendChild(button2);


    var button = document.createElement('input');
    button.type = 'button';
    button.value = 'Add Row';
    button.onclick = function(){addRow(qNumberString, specDiv)};
    rightDiv.appendChild(button);

    document.getElementById('qcontainer').appendChild(qDiv);
}