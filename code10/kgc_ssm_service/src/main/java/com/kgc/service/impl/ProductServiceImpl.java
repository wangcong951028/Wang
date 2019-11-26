package com.kgc.service.impl;

import com.github.pagehelper.PageHelper;
import com.kgc.dao.IProductDao;
import com.kgc.domain.Product;
import com.kgc.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductDao dao;

    @Override
    public List<Product> findAll(int page, int size) throws Exception {
        //参数pageNum表示页码值，参数pageSize表示每页显示条数
        PageHelper.startPage(page,size);
        return dao.findAll();
    }

    @Override
    public void save(Product product) {
        dao.save(product);
    }
}
