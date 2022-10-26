
/**Блоки кода могут повторяться и похорошему нужно сделать рефакторинг и вынести повтояющиеся блоки
 * что я и сделаю если силы останутся, но это чревато понижением читаемости кода и ещё долгой и мучительной
 * отладкой/


/**Получение кукисов и извлечение из них csrfToken для корректной передачи POST DELETE PUT PATCH запросов
 * на сервер springSecurity */
function getCookies() {
    let csrfToken = document.cookie.trim().split("=")[1];
    return csrfToken;
}


/** Функция заполнения полей данными пользователя и генераци таблицы пользователя
 * Благодаря передаче Json строки вместе с View от сервера, можно распарсить и заполнить все необходимые
 * поля и сгенерировать табилцу User-ов*/
function fillingUserChapter(idOfUserNameContainer,idOfRolesContainer,tableBodyElem ) {

    let admin = JSON.parse(document.getElementById("admin").value);
    let rolesStr = getNamesFromBigData(admin);

    let userTableData = "";
    userTableData +=
        '<tr>'
        + '<td><span>' + admin.id + '</span></td>'
        + '<td><span>' + admin.name+ '</span></td>'
        + '<td><span>' + admin.surname + '</span></td>'
        + '<td><span>' + admin.age + '</span></td>'
        + '<td><span>' + admin.email + '</span></td>'
        + '<td><span>' + rolesStr + '</span></td>'
        + '</tr>';

    document.getElementById(idOfUserNameContainer).textContent = admin.name;
    document.getElementById(idOfRolesContainer).textContent = rolesStr;
    document.getElementById(tableBodyElem).innerHTML = userTableData;

}


/**Эта функция переводит массив в троку для корректного отображения в ROLE_* в таблице User-ов */
function getNamesFromBigData(data) {
    let res = "";
    data.roles.forEach(element => {res += " " + element.name;});
    return res;
}


/**Функция заполнения таблицы User-ов(которая с кнопками Edit и Delete) и генерации html разметки*/
function  fillingUsersTable(tableTbodyId) {
    fetch("http://localhost:8080/admin/api/users")
        .then( (response) =>{
            return response.json();
        })
        .then( (data) => {
            console.log(data);
            let tableData = "";
            for(let element of data) {
                tableData += '<tr>' + '<td><span>' + element.id + '</span></td>'
                    + '<td><span>' + element.name + '</span></td>'
                    + '<td><span>' + element.surname + '</span></td>'
                    + '<td><span>' + element.age + '</span></td>'
                    + '<td><span>' + element.email + '</span></td>'
                    +  '<td><span>' + getNamesFromBigData(element) + '</span></td>'
                    + '<td><input type="button" class="btn btn-info" onclick="buttonClickHandler(event)" data-bs-toggle="modal" data-bs-target="#modalEdit" id="' + "edit_" + element.id+ '" value="Edit" /></td>'
                    + '<td><input  type="button" class="btn btn-danger" onclick="buttonClickHandler(event)" data-bs-toggle="modal" data-bs-target="#modalDelete" id="' + "delete_" + element.id + '" value="Delete"/></td>'
                    + '</tr>';
            }
            document.getElementById(tableTbodyId).innerHTML = tableData;
        });
}


/**Функция заполнения  формы модального окна */
function fillingFromData(userId, formName) {
    let parseUserId = userId.split("_").filter(idPart => idPart.match(/\d{1}/)).toString();
    fetch("http://localhost:8080/admin/api/users/" + parseUserId)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            formName.reset();
            for (let element of formName.elements) {
                switch (element.name) {
                    case "id" : element.value = data.id;
                    break;
                    case "name" : element.value  = data.name;
                    break;
                    case "surname" :element.value  = data.surname;
                    break;
                    case "age" : element.value  = data.age;
                    break;
                    case "email" : element.value  = data.email;
                    break;
                    default : break;
                }
            }
        });
}


/**Функция выбора  форм модального окна по префиксу id элемента формы*/
function buttonClickHandler(event) {
    event.preventDefault();

    let editForm = document.forms["editUserForm"];
    let deleteForm = document.forms["deleteUserForm"];

    if (event.target.id.includes("edit")) {
        fillingFromData(event.target.id, editForm);

    } else if (event.target.id.includes("delete")) {
        fillingFromData(event.target.id, deleteForm);
    }
}


/**Функция-обработчик события нажатия кнопки с id tableData для обновления таблицы User-ов*/
    function updatedAndFillingUserTable() {
        let buttonUpdater = document.getElementById("home-tab");
        buttonUpdater.addEventListener('click', {
            handleEvent(event) {
                fillingUsersTable("tableData");
            }
        });
    }


/**По причине того, что ключ в FormData не может быть "массивом", а это значит что придётся конвертировать
 * элементы массива в строку с посделующим парсингом на серверной строне. Эта функция переводит массив в троку */
function selectFromSelectToStr(selectId) {
    let createUserFormRoles = document.getElementById(selectId);
    let res = "";
    for (let option of createUserFormRoles.options) {
        if (option.selected) {
            res += option.value + " ";
        }
    }
    res = res.slice(0,-1);
    return res;
}


    /**Функция отправки запроса на удаление User-а*/
    function sendDeleteForm(event, deleteFormId, deleteModalWindowId) {
        event.preventDefault();
        let modal = bootstrap.Modal.getInstance(document.getElementById(deleteModalWindowId));
        let deleteDeleteFormData = new FormData(document.getElementById(deleteFormId));

        fetch('http://localhost:8080/admin/api/users/' + deleteDeleteFormData.get("id"), {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': getCookies()
            },
        })
            .then( (response) => {
                return response.json();
            })
            .then((data) => {
                console.log(data);
                fillingUsersTable("tableData");
                modal.hide();
            });
    }


/**Функция отправки формы на изменение пользователя*/
function sendUpdateForm(event, updateFormId, updateModalWindowId, selectId) {
    event.preventDefault();

    let modal = bootstrap.Modal.getInstance(document.getElementById(updateModalWindowId));
    let updateFormData = new FormData(document.getElementById(updateFormId));
    let updateSelectUserRoles = document.getElementById(selectId);

    let values = selectFromSelectToStr(selectId);
    updateFormData.set(updateSelectUserRoles.name, values);
    let userUpdateData = Object.fromEntries(updateFormData);

    fetch('http://localhost:8080/admin/api/users', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCookies()
        },
        body: JSON.stringify(userUpdateData)
    })
        .then( (response) => {
            return response.json();
        })
        .then((data) => {
            console.log(data);
            fillingUsersTable("tableData");
            modal.hide();
        });
}

/**Функция отправки формы на создание нового пользователя*/
function sendCreateForm(formId, submitInputId, selectId) {

    let createUserForm = document.getElementById(formId);
    let createUserSubmit = document.getElementById(submitInputId);
    let createSelectUserRoles = document.getElementById(selectId);

    createUserForm.addEventListener('submit', () => {
        event.preventDefault();
        let createUserFormData = new FormData(createUserForm);
        let values = selectFromSelectToStr(selectId);
        createUserFormData.set(createSelectUserRoles.name, values);

        let userCreateData =  Object.fromEntries(createUserFormData);
        fetch('http://localhost:8080/admin/api/users', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': getCookies()
            },
            body: JSON.stringify(userCreateData)
        })
            .then( (response) => {
                return response.json();
            })
            .then((data) =>{
                console.log(data);
            })
        createUserForm.reset();
        createUserSubmit.value = "Completed";
        setTimeout(() => {createUserSubmit.value = "Create new user";}, 2000);
    });
}

/**Вызываем функции заполнения таблиц описания User-а и таблиц списка пользователей */
fillingUsersTable("tableData");
fillingUserChapter("adminName","adminRoles", "userTableData");
updatedAndFillingUserTable();

/**Вызываем функции отправки заполненной формы создания нового пользователя*/
sendCreateForm("createUserForm", "createUserSubmit","selectCreateUserFormRoles");
