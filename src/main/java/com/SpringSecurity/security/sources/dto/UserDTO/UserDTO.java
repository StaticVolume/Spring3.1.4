package com.SpringSecurity.security.sources.dto.UserDTO;

import com.SpringSecurity.security.sources.dto.RoleDTO.RoleDTO;
import jakarta.validation.constraints.*;

import java.util.List;

public class UserDTO {

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
}
