package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.model.sdo.PaymentCdo;
import com.jsya.dongbu.model.sdo.PaymentUdo;
import com.jsya.dongbu.model.sdo.ProductCdo;
import com.jsya.dongbu.store.PaymentJpaStore;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

        return paymentJpaStore.update(payment);
    }

    private void checkHistoryFullPaid(String historyId) {
        History history = historyService.findHistoryById(historyId);
        if(history == null) return;
        int totalPrice = history.getTotalPrice();

        List<Payment> payments = paymentJpaStore.retrieveListByHistory(historyId);
        int totalPaymentPrice = payments.stream()
                .mapToInt(Payment::getPaymentPrice)
                .sum();

        boolean isFullPaid = totalPrice - totalPaymentPrice > 0;

        history.setDebtYn(false);
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
        Page<Payment> page = paymentJpaStore.retrieveListByHistoryByPage(historyId, pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<Payment> findPaymentsByMember(long memberId, Pageable pageable) {
        Page<Payment> page = paymentJpaStore.retrieveListByMemberByPage(memberId, pageable);
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
