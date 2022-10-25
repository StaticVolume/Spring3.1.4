package com.SpringSecurity.security.sources.dao;

import com.SpringSecurity.security.sources.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Role> getRoleFromBaseById(Long id) {
        return Optional.ofNullable(entityManager.find(Role.class,id));
    }

    @Override
    public void saveRoleToBaseByName(String name) {
        entityManager.persist(new Role(name));
    }

    @Override
    public Optional<Role> getRoleFromBaseByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select role from Role role where role.id = :name", Role.class)
                .setParameter("name",name);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Role> getRolesFromBase() {
        TypedQuery<Role> query = entityManager.createQuery("select role FROM Role role", Role.class);
        return query.getResultList();
    }
}
