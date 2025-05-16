package com.jsya.dongbu.repository;

import com.jsya.dongbu.store.jpo.HistoryJpo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryJpo, String> {
    List<HistoryJpo> findAll();
    Page<HistoryJpo> findByMemberId(long memberId, Pageable pageable);
}
