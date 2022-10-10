var fields = ["Name", "X", "Y", "Date", "RealHero", "HasToothpick", "ImpactSpeed", "MinutesOfWaiting", "WeaponType", "Mood", "Cool"]

fields.forEach((element) => {
    $('.is' + element + 'Disabled').change(function () {
        if ($('.is' + element + 'Disabled').is(':checked')) {
            $('.' + element).removeAttr("disabled");
        } else {
            $('.' + element).attr("disabled", "true");
        }
    });
})

function parseDate(date, time) {
    let year = date.children[0].textContent;
    let month = date.children[1].textContent;
    let day = date.children[2].textContent;
    let hours = time.children[0].textContent;
    let min = time.children[1].textContent;
    // yyyy-MM-dd HH:mm
    return year + "-" + month + "-" + day + " " + hours + ":" + min;
}

function filterListener(form, url, ev) {
    let formData = new FormData(form);
    let request = new XMLHttpRequest();
    request.responseType = 'document';
    let getStr = "?selectedPage=" + document.getElementById("selectedPage").value + "&numberOfRecordsPerPage=" + document.getElementById("numberOfRecordsPerPage").value + "&";
    for (let pair of formData.entries()) {
        getStr += pair[0] + '=' + pair[1] + '&';
    }
    getStr = url + getStr.substr(0, getStr.length - 1);
    request.open("GET", getStr);

    request.onload = function (oEvent) {
        createHumanBeingTable(request);
    };
    request.send(formData);
    ev.preventDefault();
}

const filterForm = document.forms.namedItem("filterForm");
filterForm.addEventListener('submit', function (ev) {
    filterListener(filterForm, '/lab1/humanBeings/filter', ev);
}, false);

const sortForm = document.forms.namedItem("sortForm");
sortForm.addEventListener('submit',
    function (ev) {
        let url = "/lab1/humanBeings/sort?selectedPage=" + document.getElementById("selectedPage").value + "&numberOfRecordsPerPage="+  document.getElementById("numberOfRecordsPerPage").value+ "&";
        $('input[type=radio]').filter(':checked').each(function () {
            var inputField = $(this);
            url += inputField.attr('name') + "=" + inputField.val() + "&";
        });
        url = url.substring(0, url.length - 1);
        let request = new XMLHttpRequest();
        request.responseType = 'document';
        request.open("GET", url);

        request.onload = function (oEvent) {
            createHumanBeingTable(request);
        };
        request.send();
        ev.preventDefault();
    }, false);

const filterByName = document.forms.namedItem("filterByName");
filterByName.addEventListener('submit',
    function (ev) {
        filterListener(filterByName, '/lab1/humanBeings/filter/name', ev);
    }, false);



function changePagesQuality(humanBeingsQuality) {
    const numberOfRecordsPerPage = document.getElementById("numberOfRecordsPerPage").value;
    const pagesQuality = Math.ceil(humanBeingsQuality / numberOfRecordsPerPage);
    $('#selectedPage').remove();
    let html = "<select id='selectedPage' name='selectedPage'>";
    for (let i = 1; i < pagesQuality + 1; i++) {
        html += '<option value=' + i + '>' + i + '</option>'
    }
    html += "</select>"
    $('.selectedPage').append(html);
}


function deleteHumanBeing(id) {
    let request = new XMLHttpRequest();
    request.open("DELETE", "/lab1/delete?id=" + id);
    request.onload = function (oEvent) {
        window.location = '/lab1/';
    };
    request.send();
}

function deleteByMood(e) {
    e.preventDefault();
    var mood = $("[name=delete_by_mood]").val(),
        request = new XMLHttpRequest();
    console.log(mood)
    request.open("DELETE", "/lab1/mood?mood=" + mood);
    request.onload = function (oEvent) {
        window.location = '/lab1/';
    };
    request.send();
    return false;
}

function createHumanBeingTable(request) {
    if (request.status === 200) {
        let filteredHumanBeings = [];
        let rawData = request.response.getElementsByTagName("humanBeings")[0].getElementsByTagName("humanBeings")[0];
        let k, i, j, oneRecord, oneObject, innerObject;
        for (i = 0; i < rawData.children.length; i++) {
            oneRecord = rawData.children[i];
            oneObject = filteredHumanBeings[filteredHumanBeings.length] = {};
            for (j = 0; j < oneRecord.children.length; j++) {
                if (oneRecord.children[j].children.length !== 0 && !oneRecord.children[j].tagName.includes('creationDate')) {
                    innerObject = oneObject[oneRecord.children[j].tagName] = {};
                    for (k = 0; k < oneRecord.children[j].children.length; k++) {
                        innerObject[oneRecord.children[j].children[k].tagName] = oneRecord.children[j].children[k].textContent;
                    }
                    oneObject[oneRecord.children[j].tagName] = innerObject;
                } else {
                    if (oneRecord.children[j].tagName.includes('creationDate')) {
                        let creationDate = oneRecord.children[j].children[0];
                        let creationTime = oneRecord.children[j].children[1];
                        console.log(parseDate(creationDate, creationTime));
                        oneObject[oneRecord.children[j].tagName] = parseDate(creationDate, creationTime);
                    } else {
                        oneObject[oneRecord.children[j].tagName] = oneRecord.children[j].textContent;
                    }
                }
            }
        }
        $('.table-rows').remove();
        let html;
        for (i = 0; i < filteredHumanBeings.length; i++) {
            html += "<tr class='table-rows'><td>" + filteredHumanBeings[i].id + "</td><td>" + filteredHumanBeings[i].name + "</td><td>" + filteredHumanBeings[i].coordinates.x
                + "</td><td>" + filteredHumanBeings[i].coordinates.y + "</td><td>" + filteredHumanBeings[i].creationDate + "</td><td>" + filteredHumanBeings[i].realHero
                + "</td><td>" + filteredHumanBeings[i].hasToothpick + "</td><td>" + filteredHumanBeings[i].impactSpeed
                + "</td><td>" + filteredHumanBeings[i].minutesOfWaiting + "</td><td>" + filteredHumanBeings[i].weaponType
                + "</td><td>" + filteredHumanBeings[i].mood + "</td><td>" + filteredHumanBeings[i].car.cool + "</td>" +
                "<td><a href=pages/edit-form?id=" + filteredHumanBeings[i].id + ">Edit</a>" +
                "    <button class='btn btn-primary mx-auto mt-2' onclick='deleteHumanBeing("+filteredHumanBeings[i].id+");'>Delete</button></td></tr>";
        }
        $('table').append(html);
    } else {
        console.log("Error " + request.status);
    }
}
