function setDefaultMatrixAnswer(columns, divField) {
    var valueT = ""
    for (i = 0; i < columns; i++) {
       valueT += "(-1)"
    }
    divField.value = valueT
    console.log(valueT)
}

function setDefaultValue(columns) {
    var valueT = ""
    for (i = 0; i < columns-1; i++) {
        valueT += "(-1)"
    }
    return valueT
}

function newMatrixAnswer(questionNumber, columns) {
    var id = 'question' + questionNumber.toString();

    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].tip'), 'X');


    var divField = document.createElement('div');
    divField.hidden = true;
    divField.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].answer'), setDefaultValue(columns));
    // divField.value = setDefaultValue(columns)
    // setDefaultMatrixAnswer(columns, divField)


    var divOption = document.createElement('div');
    divOption.hidden = true;
    divOption.innerHTML = newTextInputWithValue('elements['.concat(questionNumber,'].option'), '');

    var containerDiv = document.getElementById(id);
    containerDiv.appendChild(divTip);
    containerDiv.appendChild(divField);
    containerDiv.appendChild(divOption);
}

//returns index of nth occurance of char in string
function nth_occurrence (string, char, nth) {
    var first_index = string.indexOf(char);
    var length_up_to_first_index = first_index + 1;

    if (nth == 1) {
        return first_index;
    } else {
        var string_after_first_occurrence = string.slice(length_up_to_first_index);
        var next_occurrence = nth_occurrence(string_after_first_occurrence, char, nth - 1);

        if (next_occurrence === -1) {
            return -1;
        } else {
            return length_up_to_first_index + next_occurrence;
        }
    }
}

function setMatrixAnswer(counter, rowIndex, columnIndex) {

    var answerName = 'elements['.concat(counter,'].answer');
    var answerBox = document.getElementsByName(answerName).item(0);
    var answerValue = answerBox.value;

    //get element on miniCounter position and replace it with j
    var beginIndex = nth_occurrence(answerValue, '(', rowIndex+1);
    var endIndex = nth_occurrence(answerValue, ')', rowIndex+1);


    var newValue = answerValue.substring(0, beginIndex+1) + columnIndex.toString() + answerValue.substring(endIndex, answerValue.len)
    answerBox.value = newValue
}

function generateRadioMatrixButton(counter, miniCounter, j) {
    var trID = "tr" + counter.toString() + "_" + miniCounter.toString();

    var td = document.createElement('td');
    var input = document.createElement('input');
    input.type = 'radio';
    var inputName = "group" + counter + "_" + miniCounter;
    input.name =  inputName;
    input.onclick = function() {setMatrixAnswer(counter, miniCounter, j)};

    td.appendChild(input);

    var trelement = document.getElementById(trID.toString())
    trelement.appendChild(td)
}