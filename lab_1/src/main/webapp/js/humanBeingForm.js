const addHumanBeingForm = document.forms.namedItem("addHumanBeingForm");
addHumanBeingForm.addEventListener('submit', function (ev) {
    let formData = new FormData(addHumanBeingForm);
    let request = new XMLHttpRequest();
    const newHumanBeing = '<humanBeing>' +
        '         <id>0</id>' +
        '         <name>' + formData.get('name') + '</name>' +
        '         <coordinates>' +
        '            <x>' + formData.get('x') + '</x>' +
        '            <y>' + formData.get('y') + '</y>' +
        '         </coordinates>' +
        '         <creationDate>' + formData.get('creation-date') + "T" + formData.get('creation-time') + ":00.000" + '</creationDate>' +
        '         <realHero>' + formData.get('realHero') + '</realHero>' +
        '         <hasToothpick>' + formData.get('hasToothpick') + '</hasToothpick>' +
        '         <impactSpeed>' + formData.get('impactSpeed') + '</impactSpeed>' +
        '         <minutesOfWaiting>' + formData.get('minutesOfWaiting') + '</minutesOfWaiting>' +
        '         <weaponType>' + formData.get('weaponType') + '</weaponType>' +
        '         <mood>' + formData.get('mood') + '</mood>' +
        '         <car>' +
        '            <cool>' + formData.get('cool') + '</cool>' +
        '         </car>' +
        '      </humanBeing>'
    request.open("POST", "/lab1/humanBeings");
    request.send(newHumanBeing);
    request.onload = function (oEvent) {
        getErrorMsg(request);
    };
    ev.preventDefault();
}, false);

function updateHumanBeing() {
    const updateHumanBeingForm = document.forms.namedItem("updateHumanBeingForm");
    let formData = new FormData(updateHumanBeingForm);
    let request = new XMLHttpRequest();
    const newHumanBeing = '<humanBeing>' +
        '         <id>' + formData.get('id') + '</id>' +
        '         <name>' + formData.get('name') + '</name>' +
        '         <coordinates>' +
        '            <x>' + formData.get('x') + '</x>' +
        '            <y>' + formData.get('y') + '</y>' +
        '         </coordinates>' +
        '         <creationDate>' + formData.get('creation-date') + "T" + formData.get('creation-time') + ":00.000" + '</creationDate>' +
        '         <realHero>' + formData.get('realHero') + '</realHero>' +
        '         <hasToothpick>' + formData.get('hasToothpick') + '</hasToothpick>' +
        '         <impactSpeed>' + formData.get('impactSpeed') + '</impactSpeed>' +
        '         <minutesOfWaiting>' + formData.get('minutesOfWaiting') + '</minutesOfWaiting>' +
        '         <weaponType>' + formData.get('weaponType') + '</weaponType>' +
        '         <mood>' + formData.get('mood') + '</mood>' +
        '         <car>' +
        '            <cool>' + formData.get('cool') + '</cool>' +
        '         </car>' +
        '      </humanBeing>'
    request.open("PUT", "/lab1/humanBeings");
    request.onload = function (oEvent) {
        getErrorMsg(request);
    };
    request.send(newHumanBeing);
}

function getErrorMsg(request) {
    let errorMsg = "";
    if (request.status === 200) {
        window.location = '/lab1/';
    } else {
        let rawData = $.parseXML(request.response).getElementsByTagName("errors")[0].getElementsByTagName("errors")[0];
        let k, i, j, oneRecord, oneObject, innerObject;
        for (i = 0; i < rawData.children.length; i++) {
            errorMsg += rawData.children[i].children[2].textContent + "<br>"
        }
    }

    $('.error-msg__text').remove();
    $('.error-msg').append("<p class='error-msg__text alert alert-danger'>" + errorMsg + "</p>");
}
