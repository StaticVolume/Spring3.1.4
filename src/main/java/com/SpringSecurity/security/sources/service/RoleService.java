package com.SpringSecurity.security.sources.service;

import com.SpringSecurity.security.sources.model.Role;

import java.util.List;


public interface RoleService {

    public Role getRoleById(Long id);
    public void saveRole(String name);
    public Role  getRoleByName(String name);

    public List<Role> getRoles();
}
