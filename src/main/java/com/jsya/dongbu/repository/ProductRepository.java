package com.jsya.dongbu.repository;

import com.jsya.dongbu.store.jpo.ProductJpo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductJpo, String> {
    Page<ProductJpo> findByHistoryId(String historyId, Pageable pageable);
}
