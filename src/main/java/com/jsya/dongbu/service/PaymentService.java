package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Debt;
import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.model.sdo.DebtCdo;
import com.jsya.dongbu.model.sdo.DebtUdo;
import com.jsya.dongbu.model.sdo.PaymentCdo;
import com.jsya.dongbu.model.sdo.PaymentUdo;
import com.jsya.dongbu.store.PaymentJpaStore;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentJpaStore paymentJpaStore;
    private final HistoryService historyService;
    private final DebtService debtService;

    public String registerPayment(PaymentCdo paymentCdo) {
        Payment payment = new Payment(paymentCdo);
        payment.setId(paymentCdo.genId());

        // 외상 완납 여부 체크 및 등록 - 250525 debt 로직 history로 이관하며 주석처리
//        checkHistoryFullPaid(payment.getHistoryId());

        return paymentJpaStore.create(payment);
    }

    public String modifyPayment(PaymentUdo paymentUdo) {
        Payment payment = findPaymentById(paymentUdo.getId());
        payment.modifyAttributes(paymentUdo);

        // 외상 완납 여부 체크 및 등록 - 250525 debt 로직 history로 이관하며 주석처리
//        checkHistoryFullPaid(payment.getHistoryId());

        return paymentJpaStore.update(payment);
    }

    /**
     * 외상 완납 여부 체크 및 등록
     * @param historyId
     */
    private void checkHistoryFullPaid(String historyId) {
        LocalDateTime nowTime = LocalDateTime.now();

        History history = historyService.findHistoryById(historyId);
        if(history == null) return;
        int totalPrice = history.getTotalPrice();

        List<Payment> payments = paymentJpaStore.retrieveListByHistory(historyId);
        int totalPaymentPrice = payments.stream()
                .mapToInt(Payment::getPaymentPrice)
                .sum();

        int debtPrice = totalPrice - totalPaymentPrice; // 외상액 = 현재 지불금액 - 현재까지 총 지불액
        // 외상 등록
        if (debtPrice > 0) {
            // 기존 외상 존재 여부 체크
            Debt debt = debtService.findDebtsByMemberyId(history.getMemberId());
            if(debt == null) { // 신규 외상 등록
                DebtCdo debtCdo = new DebtCdo();
                debtCdo.setDebtPrice(debtPrice);
                debtCdo.setMemberId(history.getMemberId());
                debtService.registerDebt(debtCdo);
            } else{ // 외상 수정
                DebtUdo debtUdo = new DebtUdo(debt.getId(), debtPrice, nowTime);
                debtService.modifyDebt(debtUdo);
            }
            history.setDebtYn(true);
        } else {
            history.setDebtYn(false);
        }

        // 현 시점에 history가 생성이 되어있지 않아 modify 불가능함... 고민 필요
        historyService.modifyHistory(history);
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
