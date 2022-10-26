package com.SpringSecurity.security.sources.dto.UserDTO;

import com.SpringSecurity.security.sources.converterToEntity.ConverterFromTo;
import com.SpringSecurity.security.sources.dto.RoleDTO.RoleDTO;
import com.SpringSecurity.security.sources.model.Role;
import com.SpringSecurity.security.sources.model.User;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Имплементируем интерфейс конвертера и реализуем его метод для упрощенного конвентирования одного обьекта в другой*/
public class UserDTO implements ConverterFromTo<UserDTO, User> {

    @NotEmpty(message = "name must be not null")
    @Size(min = 2, max = 128, message = "name must be greater 2 characters and less 128 characters")
    private String name;

    @NotEmpty(message = "surname must be not null")
    @Size(min = 2, max = 128, message = "surname must be greater 2 characters and less 128 characters")
    private String surname;

    @Min(value = 5, message = "age should be greater than 5 years")
    @Max(value = 100, message = "age should be less then 100 years ")
    private int age;

    @NotEmpty(message = "email must be not null")
    @Email(message = "email is not correct")
    private String email;

    @NotEmpty(message = "password must be not null")
    @Size(min = 2, max = 512, message = "password must be greater 2 characters and less 128 characters")
    private String password;



    /***  @JsonManagedReference чтобы избавиться от рекурсии при маппинге спомошью Jackson. Не стал их прописывать в модель(Model)
     * так как проблема возникает на уровне маппинга с использованием Jackson*/

    private List<RoleDTO> roles;

    private String rolesStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public String getRolesStr() {
        return rolesStr;
    }

    public void setRolesStr(String rolesStr) {
        this.rolesStr = rolesStr;
    }


    /**Утилитная функция парсинга userDTO c строковым полем getRolesStr в обьект User с List<Role> */
    @Override
    public User Convert(UserDTO userDTO) {
        User user = new User();

        if (userDTO instanceof UserDTOWithID) {
            user.setId(((UserDTOWithID) userDTO).getId());
        }
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        String str = userDTO.getRolesStr();
        List<Role> rolesList = new ArrayList<>();
        Arrays.stream(str.split(" ")).toList().stream().forEach(element -> rolesList.add(new Role(element)));
        user.setRoles(rolesList);
        return user;
    }
}

