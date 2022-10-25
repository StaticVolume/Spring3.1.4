package com.SpringSecurity.security.sources.service;

import com.SpringSecurity.security.sources.dao.RoleDao;
import com.SpringSecurity.security.sources.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleById(Long id) {
        Optional<Role> role = roleDao.getRoleFromBaseById(id);
        if (role.isEmpty()) {
            throw new RuntimeException("role with id = " + id + " not found!");
        }
        return role.get();
    }

    @Override
    @Transactional
    public void saveRole(String name) {
        if (roleDao.getRoleFromBaseByName(name).isPresent()) {
            throw new RuntimeException("role with name = " + name + " is exist in database");
        }
        roleDao.saveRoleToBaseByName(name);
    }
    @Override
    public List<Role> getRoles() {
        return roleDao.getRolesFromBase();
    }

    @Override
    public Role getRoleByName(String name) {
        Optional<Role> role = roleDao.getRoleFromBaseByName(name);
        if (role.isEmpty()) {
            throw new RuntimeException("role with name = " + name + " not found");
        }
        return role.get();
    }
}
