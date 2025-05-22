package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.model.sdo.PaymentCdo;
import com.jsya.dongbu.model.sdo.PaymentUdo;
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
//    private final HistoryService historyService;

    public String registerPayment(PaymentCdo paymentCdo) {
        Payment payment = new Payment(paymentCdo);
        payment.setId(paymentCdo.genId());

        checkHistoryFullPaid(payment.getHistoryId());

        return paymentJpaStore.create(payment);
    }

    public String modifyPayment(PaymentUdo paymentUdo) {
        Payment payment = findPaymentById(paymentUdo.getId());
        payment.modifyAttributes(paymentUdo);

        checkHistoryFullPaid(payment.getHistoryId());

        return paymentJpaStore.update(payment);
    }

    private void checkHistoryFullPaid(String historyId) {
//        History history = historyService.findHistoryById(historyId);
//        if(history == null) return;
//        int totalPrice = history.getTotalPrice();
//
//        List<Payment> payments = paymentJpaStore.retrieveListByHistory(historyId);
//        int totalPaymentPrice = payments.stream()
//                .mapToInt(Payment::getPaymentPrice)
//                .sum();
//
//        history.setDebtYn(totalPrice - totalPaymentPrice > 0);
//        historyService.modifyHistory(history);
    }

    public List<Payment> findPayments() {
        return paymentJpaStore.retrieveAll();
    }

    public Payment findPaymentById(String  paymentId) {
        Optional<Payment> paymentOpt = paymentJpaStore.retrieve(paymentId);
        return paymentOpt.orElseThrow(() -> new NotFoundException("ID [%s]에 해당하는 결제내역이 없습니다."));
    }

    public PageResponse<Payment> findPaymentsByPage(Pageable pageable) {
        Page<Payment> page = paymentJpaStore.retrieveAllByPage(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public List<Payment> findPaymentsByHistory(String historyId) {
        return paymentJpaStore.retrieveListByHistory(historyId);
    }

    public PageResponse<Payment> findPaymentsByMemberByPage(long memberId, Pageable pageable) {
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
