package com.kgc.service;

import com.kgc.domain.Permission;
import com.kgc.domain.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAll(int page,int size) throws Exception;

    void save(Role role) throws Exception;

    Role findById(String roleId) throws Exception;

    List<Permission> findOtherPermission(String roleId) throws Exception;

    void addPermissionToRole(String roleId, String[] permissionIds) throws Exception;
}
