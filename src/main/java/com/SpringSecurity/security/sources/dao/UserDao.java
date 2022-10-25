package com.SpringSecurity.security.sources.dao;

import com.SpringSecurity.security.sources.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    public void addUserToDatabase(User user);

    public List<User> getAllUsersFromDatabase();

    public void deleteUserFromDatabase(User user);

    public Optional<User> getUserByIdFromDatabase(Long id);

     public void updateUserInDatabase(User user);

    Optional<User> loadUserByUsername(String username);

    public void deleteUserFromDatabaseById(Long id);
}

