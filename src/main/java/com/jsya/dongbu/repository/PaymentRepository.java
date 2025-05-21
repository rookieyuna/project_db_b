package com.jsya.dongbu.repository;

import com.jsya.dongbu.store.jpo.PaymentJpo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentJpo, String> {
    Page<PaymentJpo> findByHistoryId(String historyId, Pageable pageable);
    Page<PaymentJpo> findByMemberId(long memberId, Pageable pageable);
}
