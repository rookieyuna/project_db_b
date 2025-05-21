package com.jsya.dongbu.store.impl;

import com.jsya.dongbu.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductStore {

    String create(Product product);
    List<String> createAll(List<Product> products);
    String update(Product product);
    Optional<Product> retrieve(String id);
    List<Product> retrieveAll();
    Page<Product> retrieveList(Pageable pageable);
    Page<Product> retrieveListByHistory(String historyId, Pageable pageable);
    void delete(String id);
    boolean exists(String id);
}
