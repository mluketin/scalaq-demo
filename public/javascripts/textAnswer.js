function newTextInput(nameValue, placeholderValue) {
    return '<input id=inputdefault class=form-control name='.concat(nameValue,  ' placeholder="', placeholderValue, '" type=text>');
}

function printArg(arg) {
    console.log(arg)
}

function newTextInputAnswer(nameValue) {
    return '<input id=inputdefault class=form-control name='.concat(nameValue, ' type=text>');
}

function newTextAnswer(questionNumber) {
    var id = 'question' + questionNumber.toString()

    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].tip'), 'T');
    // divTip.innerHTML = newTextInputWithValue('tip', 'T');


    var divField = document.createElement('div');
    divField.id = 'elements['.concat(questionNumber,'].answer');

    divField.innerHTML = newTextInputAnswer('elements['.concat(questionNumber,'].answer'));
    // divField.innerHTML = newTextInputAnswer('answer');


    var divOption = document.createElement('div');
    divOption.hidden = true;
    divOption.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].option'), '');
    // divOption.innerHTML = newTextInputWithValue('option', '');

    
    
    var containerDiv = document.getElementById(id);
    containerDiv.appendChild(divTip);
    containerDiv.appendChild(divField);
    containerDiv.appendChild(divOption);



    // container.appendChild(divTip);
    // container.appendChild(divField);
}