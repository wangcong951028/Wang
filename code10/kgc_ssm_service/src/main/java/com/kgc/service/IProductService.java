package com.kgc.service;

import com.kgc.domain.Product;

import java.util.List;

public interface IProductService {

    List<Product> findAll(int page, int size) throws Exception;

    void save(Product product);

}
