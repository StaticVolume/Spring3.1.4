package com.SpringSecurity.security.sources.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**Глобальный обработчик ошибок(в основном применяется при нескольких @Controller)*/
@ControllerAdvice
public class UserGlobalExceptionHandler {

    /**Метод, перехватывающий exceptions, формирующий из текста сообщения обьект UserIncorrectData
     * и отправляющий JSON ответ на некорректный запрос(например поплытка получить пользователя по несуществующему ID)
     * При желании можно написать несколько примерно таких методов с иными входящими Exception*/
    @ExceptionHandler
    private ResponseEntity<UserIncorrectData> handleException(RuntimeException runtimeException) {
        UserIncorrectData userIncorrectData = new UserIncorrectData(runtimeException.getMessage());
        return new ResponseEntity<>(userIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
