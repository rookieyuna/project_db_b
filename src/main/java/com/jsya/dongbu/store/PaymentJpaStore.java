package com.jsya.dongbu.store;

import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.repository.PaymentRepository;
import com.jsya.dongbu.store.impl.PaymentStore;
import com.jsya.dongbu.store.jpo.PaymentJpo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PaymentJpaStore implements PaymentStore {

    private final PaymentRepository paymentRepository;

    @Override
    public String create(Payment payment) {
        PaymentJpo paymentJpo = paymentRepository.save(new PaymentJpo(payment));
        return paymentJpo.getId();
    }

    @Override
    public String update(Payment payment) {
        PaymentJpo paymentJpo = paymentRepository.save(new PaymentJpo(payment));
        return paymentJpo.getId();
    }

    @Override
    public Optional<Payment> retrieve(String id) {
        Optional<PaymentJpo> paymentJpo = paymentRepository.findById(id);
        return paymentJpo.map(PaymentJpo::toDomain);
    }

    @Override
    public List<Payment> retrieveAll() {
        List<PaymentJpo> paymentJpos = paymentRepository.findAll();
        return PaymentJpo.toDomains(paymentJpos);
    }

    @Override
    public Page<Payment> retrieveAllByPage(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(PaymentJpo::toDomain);
    }

    @Override
    public Page<Payment> retrieveListByMemberByPage(long memberId, Pageable pageable) {
        Page<PaymentJpo> page = paymentRepository.findByMemberId(memberId, pageable);
        return page.map(PaymentJpo::toDomain);
    }

    @Override
    public List<Payment> retrieveListByHistory(String historyId) {
        List<PaymentJpo> paymentJpos = paymentRepository.findByHistoryId(historyId);
        return paymentJpos.stream().map(PaymentJpo::toDomain).toList();
    }

    @Override
    public void delete(String id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public boolean exists(String id) {
        return paymentRepository.existsById(id);
    }
}