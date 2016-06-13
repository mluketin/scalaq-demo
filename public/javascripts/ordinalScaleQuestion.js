function updateInput(name, v) {

    console.log(name, v);

    var element = document.getElementsByName(name);
    console.log(element);
    element.value = v;

    // element.innerHTML = v;
}

function updateInput2(element, v) {
    element.value = v;
}

function newOQuestion() {
    var qNumber = getQuestionsCount();
    var qNumberString = qNumber.toString();

    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = newTextInputWithValue('questions['.concat(qNumberString,'].tip'), 'O');

    var divBody = document.createElement('div');
    divBody.innerHTML = newRequiredTextInput('questions['.concat(qNumberString,'].body'), 'Question body');

    var divDescription = document.createElement('div');
    divDescription.innerHTML = newTextInput('questions['.concat(qNumberString,'].description'), 'Question description');

    var specDiv = document.createElement('div');
    specDiv.class = 'spec';


    var minSelect = document.createElement('select');
    minSelect.id = 'min-select-'.concat(qNumberString);
    // minSelect.onchange = function(){updateInput('questions['.concat(qNumberString,'].spec.s1[2]'), minSelect.value)};

    var option0 = document.createElement('option');
    option0.text = '0';
    var option1 = document.createElement('option');
    option1.text = '1';
    minSelect.add(option0);
    minSelect.add(option1);

    var maxSelect = document.createElement('select');
    maxSelect.id = 'max-select-'.concat(qNumberString);
    maxSelect.onchange = function(){updateInput('questions['.concat(qNumberString,'].spec.s1[3]'), maxSelect.value)};

    var option2 = document.createElement('option');
    option2.text = '2';
    var option3 = document.createElement('option');
    option3.text = '3';
    var option4 = document.createElement('option');
    option4.text = '4';
    var option5 = document.createElement('option');
    option5.text = '5';
    var option6 = document.createElement('option');
    option6.text = '6';
    var option7 = document.createElement('option');
    option7.text = '7';
    var option8 = document.createElement('option');
    option8.text = '8';
    var option9 = document.createElement('option');
    option9.text = '9';
    var option10 = document.createElement('option');
    option10.text = '10';



    maxSelect.add(option2);
    maxSelect.add(option3);
    maxSelect.add(option4);
    maxSelect.add(option5);
    maxSelect.add(option6);
    maxSelect.add(option7);
    maxSelect.add(option8);
    maxSelect.add(option9);
    maxSelect.add(option10);

    var minNumber = document.createElement('div');
    minNumber.hidden = true;
    // minNumber.innerHTML = '<dl id=questions_'.concat(qNumberString, '_spec_s1_0_field><dt><label for=questions_', qNumberString , '_spec_s1></label></dt><dd><input id=questions_', qNumberString ,'_spec_s1 class=login-class name=questions[', qNumberString,'].spec.s1[0] type=text></dd>');

    var dl = document.createElement('dl');
    dl.id = 'questions_'.concat(qNumberString, '_spec_s1_0_field');

    var dt = document.createElement('dt');
    var label = document.createElement('label');
    label.for = 'questions_'.concat(qNumberString , '_spec_s1');
    dt.appendChild(label);
    dl.appendChild(dt);

    var dd = document.createElement('dd');
    var input = document.createElement('input');
    input.id = 'questions_'.concat(qNumberString ,'_spec_s1');
    input.class = 'login-class';
    input.type = 'text';
    input.name = 'questions['.concat(qNumberString,'].spec.s1[0]');
    input.value = 0;
    input.innerHTML = '0';


    minNumber.appendChild(dl);
    dl.appendChild(dd);
    dd.appendChild(input);
    minSelect.onchange = function(){updateInput2(input, minSelect.value)};



    var maxNumber = document.createElement('div');
    maxNumber.hidden = true;
    // maxNumber.innerHTML = '<dl id=questions_'.concat(qNumberString, '_spec_s1_1_field><dt><label for=questions_', qNumberString , '_spec_s1></label></dt><dd><input id=questions_', qNumberString ,'_spec_s1 class=login-class name=questions[', qNumberString,'].spec.s1[1] type=text></dd>');

    var dl2 = document.createElement('dl');
    dl2.id = 'questions_'.concat(qNumberString, '_spec_s1_1_field');

    var dt2 = document.createElement('dt');
    var label2 = document.createElement('label');
    label2.for = 'questions_'.concat(qNumberString , '_spec_s1');
    dt2.appendChild(label2);
    dl2.appendChild(dt2);

    var dd2 = document.createElement('dd');
    var input2 = document.createElement('input');
    input2.id = 'questions_'.concat(qNumberString ,'_spec_s1');
    input2.class = 'login-class';
    input2.type = 'text';
    input2.name = 'questions['.concat(qNumberString,'].spec.s1[1]');
    input2.value = 2;
    input2.innerHTML = '2';


    maxNumber.appendChild(dl2);
    dl2.appendChild(dd2);
    dd2.appendChild(input2);
    maxSelect.onchange = function(){updateInput2(input2, maxSelect.value)};


    var minLabel = document.createElement('div');
    minLabel.innerHTML = newTextInput('questions['.concat(qNumberString,'].spec.s1[2]'), 'left label');
    // minLabel.innerHTML = '<dl id=questions_'.concat(qNumberString, '_spec_s1_2_field><dt><label for=questions_', qNumberString , '_spec_s1></label></dt><dd><input id=questions_', qNumberString ,'_spec_s1 class=login-class name=questions[', qNumberString,'].spec.s1[2] type=text></dd>');

    var maxLabel = document.createElement('div');
    maxLabel.innerHTML = newTextInput('questions['.concat(qNumberString,'].spec.s1[3]'), 'right label');
    // maxLabel.innerHTML = '<dl id=questions_'.concat(qNumberString, '_spec_s1_3_field><dt><label for=questions_', qNumberString , '_spec_s1></label></dt><dd><input id=questions_', qNumberString ,'_spec_s1 class=login-class name=questions[', qNumberString,'].spec.s1[3] type=text></dd>');



    var qDiv = document.createElement('div');
    qDiv.className = 'question-class';

    qDiv.appendChild(divTip);
    qDiv.appendChild(divBody);
    qDiv.appendChild(divDescription);
    qDiv.appendChild(specDiv);
    qDiv.appendChild(minSelect);
    qDiv.appendChild(maxSelect);
    qDiv.appendChild(minNumber);
    qDiv.appendChild(maxNumber);
    qDiv.appendChild(minLabel);
    qDiv.appendChild(maxLabel);
    
    document.getElementById('qcontainer').appendChild(qDiv);
}