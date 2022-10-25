package com.SpringSecurity.security.sources.dto.RoleDTO;

import com.SpringSecurity.security.sources.dto.UserDTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RoleDTO {

    @NotEmpty(message = "name must be not null")
    @Size(min = 3, max = 128, message = "password must be greater 2 characters and less 128 characters")
    private String name;


    private String usersStr;
    private List<UserDTO> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /***  @JsonManagedReference и @JsonBackReference используют чтобы избавиться от рекурсии при маппинге спомошью Jackson. Не стал их прописывать в модель(Model)
     * так как проблема возникает на уровне маппинга с использованием Jackson. Так же при совместном использовании данных анотаций (на разных полях в разных классах)
     * у меня возникала ошибка , пришлось отказаться от  @JsonManagedReference*/
    @JsonBackReference
    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public String getUsersStr() {
        return usersStr;
    }

    public void setUsersStr(String usersStr) {
        this.usersStr = usersStr;
    }
}
