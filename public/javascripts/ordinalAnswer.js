function newOrdinalAnswer(questionNumber) {
    var id = 'question' + questionNumber.toString()

    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].tip'), 'O');


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

function setOrdinalAnswer(name, valueT) {
    console.log(name, valueT);
    var inputBox = document.getElementsByName(name).item(0)
    inputBox.value = valueT.toString()
    // inputBox.value ='sad'
    // input.innerHTML = valueT;
    // input.text = valutT;


    console.log(inputBox, inputBox.value)

    // var i = document.getElementsByName('elements[0].answer');
// i.value = "4"

}

function generateRadioButton(answerNumber, questionNumber) {
    var id = 'label' + answerNumber.toString()

    var ordInputName = 'elements['.concat(questionNumber,'].answer');


    var namevalue = getRadioButtonName();
    console.log(namevalue)

    var div = document.createElement('div');
    var input = document.createElement('input');
    input.type = 'radio';
    input.name = 'group'.concat(questionNumber.toString());
    input.onclick = function(){setOrdinalAnswer(ordInputName, answerNumber)};
    // input.onclick = function(){updateInput(ordInputName, answerNumber)};


    div.appendChild(input);

    // document.write('<div><input type=radio name="' + namevalue + '"></div>');


    var label = document.getElementById(id);
    label.appendChild(div);
}