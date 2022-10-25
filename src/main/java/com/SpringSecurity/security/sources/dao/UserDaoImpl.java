package com.SpringSecurity.security.sources.dao;

import com.SpringSecurity.security.sources.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    /**пришлось убрать коструктор с параметром EntityManager потому что после перехода на SpringBoot
     * от нас ушёл файл конфигурации с определением бина EntityManager, и сейчас
     * в реализованном кострукторе с этим параметром идет мерзкое красное подчёркивание
     * бесит
     * */
   @PersistenceContext
    private   EntityManager entityManager;

    @Override
    public void addUserToDatabase(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsersFromDatabase() {
        TypedQuery<User> query = entityManager.createQuery("select user  FROM  User user", User.class);
        return query.getResultList();

    }

    @Override
    public void deleteUserFromDatabaseById(Long id) {
        entityManager.remove(getUserByIdFromDatabase(id).get());
    }
    @Override
    public void deleteUserFromDatabase(User user) {
        entityManager.remove(user);
    }

    @Override
    public Optional<User> getUserByIdFromDatabase(Long id) {

        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public void updateUserInDatabase(User user) {
        entityManager.merge(user);
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
        try {
            TypedQuery<User> query = entityManager.createQuery("select user FROM User user " +
                            "JOIN fetch user.roles roles " +
                            "where user.name = :username", User.class)
                    .setParameter("username",username);
            return Optional.ofNullable(query.getSingleResult());
        } catch (RuntimeException ex) {
            throw new RuntimeException ("user with + " + username + " not found");
        }
    }
}
/** Есть ещё вариант написать функцию
 * TypedQuery<Long> query = entityManager.createQuery("select user.id From User user where user.name = :username", Long.class).setParameter("username",username);
 * return Optional.ofNullable(entityManager.find(User.class, query.getSingleResult()));
 * */