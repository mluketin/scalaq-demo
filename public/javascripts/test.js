function myfunction() {
    var qNumber = parseInt(document.getElementById('num-of-q').innerHTML);
    var qNumberString = qNumber.toString();

    var qDiv = document.createElement('div');
    qDiv.className = 'question-class';


    var divTip = document.createElement('div');
    divTip.hidden = true;
    divTip.innerHTML = '<dl id=questions_'.concat(qNumberString, '_tip_field><dt><label for=questions_', qNumberString , '_tip></label></dt><dd><input id=questions_', qNumberString ,'_tip class=login-class name=questions[', qNumberString,'].tip type=text value=T></dd>');
    qDiv.appendChild(divTip);

    var divBody = document.createElement('div');
    divBody.innerHTML = '<dl id=questions_'.concat(qNumberString, '_body_field><dt><label for=questions_', qNumberString , '_body>Body</label></dt><dd><input id=questions_', qNumberString ,'_body class=login-class name=questions[', qNumberString,'].body type=text></dd>');
    qDiv.appendChild(divBody);

    var divDescription = document.createElement('div');
    divDescription.innerHTML = '<dl id=questions_'.concat(qNumberString, '_description_field><dt><label for=questions_', qNumberString , '_description>Description</label></dt><dd><input id=questions_', qNumberString ,'_description class=login-class name=questions[', qNumberString,'].description type=text></dd>');
    qDiv.appendChild(divDescription);

    document.getElementById('qcontainer').appendChild(qDiv);

    qNumber += 1;
    document.getElementById('num-of-q').innerHTML = qNumber;
}