package com.jsya.dongbu.repository;

import com.jsya.dongbu.store.jpo.ProductJpo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductJpo, String> {
    List<ProductJpo> findByHistoryId(String historyId);
    Page<ProductJpo> findByMemberId(long memberId, Pageable pageable);
}
