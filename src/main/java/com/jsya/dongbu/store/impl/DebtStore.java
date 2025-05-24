package com.jsya.dongbu.store.impl;

import com.jsya.dongbu.model.Debt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DebtStore {

    long create(Debt debt);
    long update(Debt debt);
    Optional<Debt> retrieve(long id);
    List<Debt> retrieveAll();
    Page<Debt> retrieveAllByPage(Pageable pageable);
    Page<Debt> retrieveListByMemberByPage(long memberId, Pageable pageable);
    void delete(long id);
    boolean exists(long id);
}
