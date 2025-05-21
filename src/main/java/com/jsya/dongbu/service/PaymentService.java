package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.model.sdo.PaymentCdo;
import com.jsya.dongbu.model.sdo.PaymentUdo;
import com.jsya.dongbu.store.PaymentJpaStore;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentJpaStore paymentJpaStore;
    private final HistoryService historyService;

    public String registerPayment(PaymentCdo paymentCdo) {
        String paymentId = paymentCdo.genId();
        Payment payment = new Payment(paymentCdo);

        payment.setId(paymentId);
        return paymentJpaStore.create(payment);
    }

    public String modifyPayment(PaymentUdo paymentUdo) {
        Payment payment = findPaymentById(paymentUdo.getId());
        payment.modifyAttributes(paymentUdo);
        //TODO: price 변경됐을경우 해당하는 History debtYn 확인 로직 추가

        return paymentJpaStore.update(payment);
    }

    public List<Payment> findPayments() {
        return paymentJpaStore.retrieveAll();
    }

    public Payment findPaymentById(String  paymentId) {
        Optional<Payment> paymentOpt = paymentJpaStore.retrieve(paymentId);
        return paymentOpt.orElseThrow(() -> new NotFoundException("결제내역이 존재하지 않습니다."));
    }

    public PageResponse<Payment> findPaymentsByPage(Pageable pageable) {
        Page<Payment> page = paymentJpaStore.retrieveList(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<Payment> findPaymentsByHistory(String historyId, Pageable pageable) {
        Page<Payment> page = paymentJpaStore.retrieveListByHistory(historyId, pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void removePayment(String paymentId) {
        paymentJpaStore.delete(paymentId);
    }

    public boolean isPaymentExists(String paymentId) {
        return paymentJpaStore.exists(paymentId);
    }
}
