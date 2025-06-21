package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Debt;
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
    private final DebtService debtService;

    private final LocalDateTime nowTime = LocalDateTime.now();

    public String registerHistory(HistoryCdo historyCdo) {
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

            // todo 기존에 미수가 있었으면 미수 금액 먼저 차감하는 걸로? - 고민... 미수는 아예 별개로 가야할지
            checkHistoryFullPaid("paid", memberId, prepaidPrice);
        }

        // 미수 금액 = 총 결제 해야 할 금액 - 현재 지불 금액
        int debtPrice = totalPrice - prepaidPrice;
        // 미수 금액 완납 여부 체크 및 등록
        history.setDebtYn(checkHistoryFullPaid("new", memberId, debtPrice));

        return historyJpaStore.create(history);
    }

    /**
     * 외상 완납 여부 체크 및 등록/수정
     * @param memberId, price
     */
    private boolean checkHistoryFullPaid(String flag, long memberId, int price) {
        if (price > 0) {
            // 기존 외상 존재 여부 체크
            Debt debt = debtService.findDebtsByMemberyId(memberId);

            if(debt == null) { // 신규 외상 등록
                DebtCdo debtCdo = new DebtCdo();
                debtCdo.setDebtPrice(price);
                debtCdo.setMemberId(memberId);
                debtService.registerDebt(debtCdo);
            } else { // 기존 외상 수정
                DebtUdo debtUdo = new DebtUdo();
                debtUdo.setId(debt.getId());

                if (flag.equals("paid")) { // 미수 금액 회수 시
                    debtUdo.setDebtPrice(debt.getDebtPrice() - price); // 기존 미수 금액 차감
                    debtUdo.setPaidDate(nowTime);

                    // todo 총 미수액 0원 시 미수 삭제 로직 추가 필요
                    // ...
                } else { // 추가 미수 발생 시
                    debtUdo.setDebtPrice(debt.getDebtPrice() + price); // 기존 미수 금액에 추가
                }
                debtService.modifyDebt(debtUdo);
            }
            return true;
        } else {
            return false;
        }
    }

    public String modifyHistory(History history) {
        return historyJpaStore.update(history);
    }

    public String modifyHistory(HistoryUdo historyUdo) {
        History history = findHistoryById(historyUdo.getId());

        history.modifyAttributes(historyUdo);

        // todo: payment, debt 수정 로직 추가 필요해보임
        // ...

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
