package com.kgc.service;

import com.kgc.domain.Orders;

import java.util.List;

public interface IOrdersService {
    List<Orders> findAll(int page, int size) throws Exception;

    //根据id查询订单详情信息
    Orders findById(String ordersId)throws Exception;
}
