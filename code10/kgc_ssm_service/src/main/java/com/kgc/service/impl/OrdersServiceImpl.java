package com.kgc.service.impl;

import com.github.pagehelper.PageHelper;
import com.kgc.dao.IOrdersDao;
import com.kgc.domain.Orders;
import com.kgc.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {
    @Autowired
    private IOrdersDao dao;
    @Override
    public List<Orders> findAll(int page, int size) throws Exception {
        //参数pageNum表示页码值，参数pageSize表示每页显示条数
        PageHelper.startPage(page,size);
        return dao.findAll();
    }

    @Override
    public Orders findById(String ordersId) throws Exception{
        return dao.findById(ordersId);
    }
}
