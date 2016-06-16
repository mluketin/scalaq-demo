function newMultiAnswer(questionNumber) {
    var id = 'question' + questionNumber.toString();

    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].tip'), 'M');


    var divField = document.createElement('div');
    divField.hidden = true;
    divField.innerHTML = newTextInputAnswer('elements['.concat(questionNumber,'].answer'));

    var divOption = document.createElement('div');
    divOption.hidden = true;
    divOption.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].option'), '');

    var containerDiv = document.getElementById(id);
    containerDiv.appendChild(divTip);
    containerDiv.appendChild(divField);
    containerDiv.appendChild(divOption);

}



function checkAnswer(index, questionNumber) {
    var answerName = 'elements['.concat(questionNumber,'].answer');
    var answerBox = document.getElementsByName(answerName).item(0);
    var answerValue = answerBox.value;

    var indexOfAnswer = answerValue.indexOf(index);
    if(indexOfAnswer == -1) { //answer not found

        if(answerValue.length == 0) {
            answerBox.value = answerBox.value + index
        } else {
            answerBox.value = answerBox.value + "_" + index

        }
    }
    else //index is found
    {
        var len = index.toString().length;
        console.log("LEN: " + len)

        if(len == answerValue.length) { //only one answer
            answerBox.value = ''
        }
        else
        {
            if(indexOfAnswer == 0) { //begining
                var newValue = answerValue.substring(len+1, answerValue.length);
                answerBox.value = newValue;
            }
            else if(indexOfAnswer + len == answerValue.length) //na kraju je
            {
                var newValue = answerValue.substring(0, indexOfAnswer-1);
                answerBox.value = newValue;
            }
            else
            {
                var newValue = answerValue.substring(0, indexOfAnswer) + answerValue.substring(indexOfAnswer+len+1, answerValue.length);
                answerBox.value = newValue;
            }
        }
    }
    console.log("AnswerBoxValue = " + answerBox.value)

}

function generateCheckBoxes(index, questionNumber, label) {
    var id = 'question' + questionNumber.toString();


    var div = document.createElement('div');

    var span1 = document.createElement('span');
    var span2 = document.createElement('span');

    var input = document.createElement('input');
    input.type = 'checkbox';
    input.onclick = function() {checkAnswer(index, questionNumber)};

    span1.appendChild(input);
    span2.innerHTML = " " + label;

    div.appendChild(span1);
    div.appendChild(span2);

    var containerDiv = document.getElementById(id);
    containerDiv.appendChild(div);

}

function checkOther(questionNumber, textInput, checkbox) {
    var answerName = 'elements['.concat(questionNumber,'].option');
    var answerBox = document.getElementsByName(answerName).item(0);

    if(checkbox.checked == 'true') {
        answerBox.value = textInput.value;
    }
    else
    {
        answerBox.value = '';
    }

}

function updateOption(checkbox, textInput, questionNumber) {
    var answerName = 'elements['.concat(questionNumber,'].option');
    var answerBox = document.getElementsByName(answerName).item(0);

    if(checkbox.checked == true) {
        answerBox.value = textInput.value;
    }

}

function generateOtherCheckBox(questionNumber, placeholder) {
    var id = 'question' + questionNumber.toString();

    var div = document.createElement('div');

    var span1 = document.createElement('span');
    var span2 = document.createElement('span');

    var textInput = document.createElement('input');
    textInput.type = 'text';
    textInput.placeholder = placeholder;

    var input = document.createElement('input');
    input.type = 'checkbox';
    input.onclick = function() {checkOther(questionNumber, textInput, input)};

    span1.appendChild(input);
    span2.appendChild(textInput);

    div.appendChild(span1);
    div.appendChild(span2);

    var containerDiv = document.getElementById(id);
    containerDiv.appendChild(div);
}