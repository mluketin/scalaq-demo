function countTableRows() {
    var body = document.getElementById('all-q-body');
    var element = body.childElementCount;
    return element.toString();
}


function createSwitchLabelChecked() {

    var wrapper = document.createElement('div');

    // var label = document.createElement('label');
    // label.class = "switch";

    var input = document.createElement('input');
    input.type = "checkbox";
    input.checked = 'true';

    // var slider = document.createElement('div');
    // slider.class = 'slider round';

    // label.appendChild(input);
    // label.appendChild(slider);

    // wrapper.appendChild(label);

    wrapper.appendChild(input);


    // return wrapper.innerHTML;
    document.write(wrapper.innerHTML);

}

function createSwitchLabelUnChecked() {
    var wrapper = document.createElement('div');

    // var label = document.createElement('label');
    // label.class = "switch";

    var input = document.createElement('input');
    input.type = "checkbox";
    input.checked = 'false';
    var name = 'disable' + countTableRows();
    input.onclick = function(){clickOnBtn(name)};

    // var slider = document.createElement('div');
    // slider.class = 'slider round';

    // label.appendChild(input);
    // label.appendChild(slider);

    wrapper.appendChild(label);

    wrapper.appendChild(input);


    document.write(wrapper.innerHTML);
}

function createSwitchButton() {
    var input = document.createElement('input');
    input.class = 'btn btn-danger';
    input.type = 'submit';
    input.value = 'Disable';
    input.name = 'disable' + countTableRows;

    var wrapper = document.createElement('div');
    wrapper.appendChild(input);
    // return wrapper.innerHTML;
    document.write(wrapper.innerHTML);

}

function clickOnBtn(btnName) {
    var body = document.getElementByName(btnName);
    body.click();
}