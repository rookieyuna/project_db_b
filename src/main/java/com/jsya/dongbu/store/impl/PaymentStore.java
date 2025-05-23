package com.jsya.dongbu.store.impl;

import com.jsya.dongbu.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PaymentStore {

    String create(Payment payment);
    String update(Payment payment);
    Optional<Payment> retrieve(String id);
    List<Payment> retrieveAll();
    Page<Payment> retrieveAllByPage(Pageable pageable);
    List<Payment> retrieveListByHistory(String historyId);
    Page<Payment> retrieveListByMemberByPage(long memberId, Pageable pageable);
    void delete(String id);
    boolean exists(String id);
}
