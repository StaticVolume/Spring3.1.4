package com.SpringSecurity.security.sources.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;



@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "name must be not null")
    @Size(min = 2, max = 128, message = "name must be greater 2 characters and less 128 characters")
    private String name;

    @Column(name = "second_name")
    @NotEmpty(message = "surname must be not null")
    @Size(min = 2, max = 128, message = "surname must be greater 2 characters and less 128 characters")
    private String surname;

    @Column(name = "age")
    @Min(value = 5, message = "age should be greater than 5 years")
    @Max(value = 100, message = "age should be less then 100 years ")
    private int age;
    @Column(name = "email")
    @NotEmpty(message = "email must be not null")
    @Email(message = "email is not correct")
    private String email;

    @Column(name = "u_password")
    @NotEmpty(message = "password must be not null")
    @Size(min = 2, max = 512, message = "password must be greater 2 characters and less 128 characters")
    private String password;


  // @JsonManagedReference
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable (
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public User() {

    }


    public User(String name, String surname, int age, String email, List<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
