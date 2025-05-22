package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.History;

import com.jsya.dongbu.model.Member;
import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.model.sdo.*;
import com.jsya.dongbu.store.HistoryJpaStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class HistoryService {

    private final HistoryJpaStore historyJpaStore;
    private final MemberService memberService;
    private final ProductService productService;
    private final PaymentService paymentService;

    public String registerHistory(HistoryCdo historyCdo) {
        LocalDateTime nowTime = LocalDateTime.now();
        String historyId = historyCdo.genId(nowTime);
        long memberId = historyCdo.getMemberId();
        int prepaidPrice = 0;

        // productList 로 totalPrice 계산
        int totalPrice = Arrays.stream(historyCdo.getProductCdos())
                .mapToInt(ProductCdo::getPrice)
                .sum();

        History history = new History(historyCdo);
        history.setId(historyId);
        history.setStartDate(nowTime);
        history.setTotalPrice(totalPrice);

        // product 등록
        List<String> productIds = Arrays.stream(historyCdo.getProductCdos())
                .map(productCdo -> {
                    productCdo.setHistoryId(historyId);
                    productCdo.setMemberId(memberId);
                    return productService.registerProduct(productCdo);
                })
                .toList();

        // payment 등록
        PaymentCdo paymentCdo = historyCdo.getPaymentCdo();
        if (paymentCdo != null) {
            paymentCdo.setHistoryId(historyId);
            paymentCdo.setMemberId(memberId);
            prepaidPrice = paymentCdo.getPaymentPrice();
            paymentService.registerPayment(paymentCdo);
        }

        // 미수여부 설정
        if(totalPrice - prepaidPrice > 0) {
            history.setDebtYn(true);
        }

        return historyJpaStore.create(history);
    }

    public String modifyHistory(History history) {
        return historyJpaStore.update(history);
    }

    public String modifyHistory(HistoryUdo historyUdo) {
        History history = findHistoryById(historyUdo.getId());

        history.modifyAttributes(historyUdo);
        return historyJpaStore.update(history);
    }

    public List<HistoryRdo> findHistorys() {
        List<History> historys = historyJpaStore.retrieveAll();

        return historys.stream().map(history -> {
            Member member = memberService.findMemberById(history.getMemberId());
            List<ProductRdo> productRdos = productService.findProductsByHistoryId(history.getId());
            List<Payment> payments = paymentService.findPaymentsByHistory(history.getId());

            return new HistoryRdo(history, member, productRdos, payments);
        }).toList();
    }

    public History findHistoryById(String historyId) {
        Optional<History> historyOpt = historyJpaStore.retrieve(historyId);
        return historyOpt.orElseThrow(() -> new NotFoundException("히스토리가 존재하지 않습니다."));
    }

    public PageResponse<History> findHistorysByPage(Pageable pageable) {
        Page<History> page = historyJpaStore.retrieveAllByPage(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<History> findHistorysByMember(long memberId, Pageable pageable) {
        Page<History> page = historyJpaStore.retrieveListByMemberByPage(memberId, pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void removeHistory(String historyId) {
        historyJpaStore.delete(historyId);
    }

    public boolean isHistoryExists(String historyId) {
        return historyJpaStore.exists(historyId);
    }
}
