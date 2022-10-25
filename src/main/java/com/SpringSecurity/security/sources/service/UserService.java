package com.SpringSecurity.security.sources.service;

import com.SpringSecurity.security.sources.model.User;

import java.util.List;

public interface UserService {

    public void addUser(User user);

    public List<User> getAllUsers();

    public User getUser(Long id);

    public void updateUser(User user);
    public void deleteUserById(Long id);
    public void deleteUser(User user);
    public User getUserByName(String name);
}

