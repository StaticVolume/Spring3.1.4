
function fillingUserChapter(idOfElement, tableBodyElem) {

    let userNameFromNode = document.getElementById(idOfElement).innerText;
    console.log(userNameFromNode);
    fetch("http://localhost:8080/admin/api/user/" + userNameFromNode)
        .then( (response) => {
            return response.json();
        })
        .then( (data) => {
            console.log(data);
            let userTableData = "";
            let res = "";
            console.log(data.roles.forEach( element => { element.name += " ";}));
            userTableData += '<tr>' + '<td><span>' + data.id + '</span></td>'
                + '<td><span>' + data.name + '</span></td>'
                + '<td><span>' + data.surname + '</span></td>'
                + '<td><span>' + data.age + '</span></td>'
                + '<td><span>' + data.email + '</span></td>'
                + '<td><span>' + getNamesFromBigData(data) + '</span></td>'
                + '</tr>';

            document.getElementById(tableBodyElem).innerHTML=userTableData;
        });
}

function getNamesFromBigData(data) {
    let res = "";
    data.roles.forEach(element => {res += " " + element.name;});
    console.log(res);
    return res;
}



fillingUserChapter("userName", "userTableData");