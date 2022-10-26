/** Функция заполнения полей данными пользователя и генераци таблицы пользователей
 * Благодаря передаче Json строки вместе с View от сервера, можно распарсить и заполнить все необходимые
 * поля и сгенерировать табилцу User-ов*/
function fillingUserChapter(idOfUserNameContainer,idOfRolesContainer,tableBodyElem ) {

    let user = JSON.parse(document.getElementById("user").value);
    let rolesStr = getNamesFromBigData(user);

    let userTableData = "";
        userTableData +=
        '<tr>'
        + '<td><span>' + user.id + '</span></td>'
        + '<td><span>' + user.name+ '</span></td>'
        + '<td><span>' + user.surname + '</span></td>'
        + '<td><span>' + user.age + '</span></td>'
        + '<td><span>' + user.email + '</span></td>'
        + '<td><span>' + rolesStr + '</span></td>'
        + '</tr>';

    document.getElementById(idOfUserNameContainer).textContent = user.name;
    document.getElementById(idOfRolesContainer).textContent = rolesStr;
    document.getElementById(tableBodyElem).innerHTML = userTableData;

}

function getNamesFromBigData(data) {
    let res = "";
    data.roles.forEach(element => {res += " " + element.name;});
    return res;
}




fillingUserChapter("userName","userRoles", "userTableData");
