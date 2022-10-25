package com.SpringSecurity.security.sources.dao;

import com.SpringSecurity.security.sources.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    public Optional<Role> getRoleFromBaseById(Long id);

    public void saveRoleToBaseByName(String name);

    public Optional<Role> getRoleFromBaseByName(String name);


    public List<Role> getRolesFromBase();
}
