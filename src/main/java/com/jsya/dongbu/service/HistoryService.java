package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.History;

import com.jsya.dongbu.model.Member;
import com.jsya.dongbu.model.sdo.HistoryCdo;
import com.jsya.dongbu.model.sdo.HistoryRdo;
import com.jsya.dongbu.model.sdo.HistoryUdo;
import com.jsya.dongbu.model.sdo.ProductCdo;
import com.jsya.dongbu.store.HistoryJpaStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class HistoryService {

    private final MemberService memberService;
    private final HistoryJpaStore historyJpaStore;

    public String registerHistory(HistoryCdo historyCdo) {
        long now = System.currentTimeMillis();
        String historyId = historyCdo.genId(now);
        int price = Arrays.stream(historyCdo.getProductCdos())
                .mapToInt(ProductCdo::getPrice)
                .sum();

        History history = new History(historyCdo);
        history.setId(historyId);
        history.setStartDate(now);
        history.setTotalPrice(price);

        if(history.getTotalPrice() - history.getPrepaidPrice() > 0) {
            history.setDebtYn(true);
        }

        //TODO: productCdos 등록

        return historyJpaStore.create(history);
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
            return new HistoryRdo(history, member);
        }).toList();
    }

    public History findHistoryById(String historyId) {
        Optional<History> historyOpt = historyJpaStore.retrieve(historyId);
        return historyOpt.orElseThrow(() -> new NotFoundException("히스토리가 존재하지 않습니다."));
    }

    public PageResponse<History> findHistorysByPage(Pageable pageable) {
        Page<History> page = historyJpaStore.retrieveList(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<History> findHistorysByMember(long memberId, Pageable pageable) {
        Page<History> page = historyJpaStore.retrieveListByMember(memberId, pageable);
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
