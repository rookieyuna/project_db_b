package com.jsya.dongbu.repository;

import com.jsya.dongbu.store.jpo.DebtJpo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DebtRepository extends JpaRepository<DebtJpo, Long> {
    List<DebtJpo> findByDebtId(long debtId);
    Optional<DebtJpo> findByMemberId(long memberId);
    Page<DebtJpo> findByMemberId(long memberId, Pageable pageable);
}
