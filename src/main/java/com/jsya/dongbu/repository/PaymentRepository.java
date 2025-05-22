package com.jsya.dongbu.repository;

import com.jsya.dongbu.store.jpo.PaymentJpo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentJpo, String> {
    List<PaymentJpo> findByHistoryId(String historyId);
    Page<PaymentJpo> findByHistoryId(String historyId, Pageable pageable);
    Page<PaymentJpo> findByMemberId(long memberId, Pageable pageable);
}
