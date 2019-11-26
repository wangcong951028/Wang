package com.kgc.service.impl;

import com.github.pagehelper.PageHelper;
import com.kgc.dao.IPermissionsDao;
import com.kgc.domain.Permission;
import com.kgc.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private IPermissionsDao dao;
    @Override
    public List<Permission> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return dao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
        dao.save(permission);
    }
}
