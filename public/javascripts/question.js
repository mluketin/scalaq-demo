function newTextInput(nameValue, placeholderValue) {
 return '<input id=inputdefault class=form-control name='.concat(nameValue,  ' placeholder="', placeholderValue, '" type=text>');
}

function newRequiredTextInput(nameValue, placeholderValue) {
    return '<input required id=inputdefault class=form-control name='.concat(nameValue,  ' placeholder="', placeholderValue, '" type=text>');
}


function newTextInputWithValue(nameValue, valueValue) {
    return '<input id=inputdefault class=form-control name='.concat(nameValue, ' type=text value=', valueValue, '>');
}

function newTQuestion() {
    var qNumber = getQuestionsCount();
    var qNumberString = qNumber.toString();

    //div za input za TIP pitanja
    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = newTextInputWithValue('questions['.concat(qNumberString,'].tip'), 'T');

    //div za input za BODY pitanja
    var divBody = document.createElement('div');
    divBody.innerHTML = newRequiredTextInput('questions['.concat(qNumberString,'].body'), 'Question body');

    //div za input za DESCRIPTON
    var divDescription = document.createElement('div');
    divDescription.innerHTML = newTextInput('questions['.concat(qNumberString,'].description'), 'Question description');


    var qDiv = document.createElement('div');
    qDiv.className = 'question-class';
    //
    // qDiv.appendChild(divTip);
    // qDiv.appendChild(divBody);
    // qDiv.appendChild(divDescription);
    // document.getElementById('qcontainer').appendChild(qDiv);


    var panelGroup = document.createElement('div');
    panelGroup.class = 'panel-group';

    var  panelInfo = document.createElement('div');
    panelInfo.class = 'panel panel-info';

    var  panelBody = document.createElement('div');
    panelBody.class = 'panel-body';

    var  panelBody2 = document.createElement('div');
    panelBody2.class = 'panel-body';

    qDiv.appendChild(panelGroup)
    panelGroup.appendChild(panelInfo);
    panelInfo.appendChild(panelBody);
    panelGroup.appendChild(panelBody2);

    // var newLine3 = document.createElement('br');

    panelBody.appendChild(divTip);
    // panelBody.appendChild(newLine3);

    panelBody.appendChild(divBody);
    // var newLine = document.createElement('br');
    // panelBody.appendChild(newLine);
    panelBody.appendChild(divDescription);
    // var newLine2 = document.createElement('br');
    // panelBody.appendChild(newLine2);

    document.getElementById('qcontainer').appendChild(qDiv);
}

function addOption(qNumberString, specDiv) {
    var answersCount = specDiv.childElementCount;
    var aNumberString = answersCount.toString();

    var div = document.createElement('div');

    div.innerHTML = newRequiredTextInput('questions['.concat(qNumberString,'].spec.s1[',answersCount,']'), 'Option '.concat(aNumberString));
    // div.innerHTML = '<dl id=questions_'.concat(qNumberString, '_spec_s1_', aNumberString ,'_field><dt><label for=questions_', qNumberString , '_spec_s1></label></dt><dd><input id=questions_', qNumberString ,'_spec_s1 class=login-class name=questions[', qNumberString,'].spec.s1[',answersCount,'] type=text></dd>');

    specDiv.appendChild(div)
}
