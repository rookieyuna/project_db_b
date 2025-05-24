package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Debt;
import com.jsya.dongbu.model.sdo.DebtCdo;
import com.jsya.dongbu.model.sdo.DebtUdo;
import com.jsya.dongbu.store.DebtJpaStore;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DebtService {

    private final DebtJpaStore debtJpaStore;

    public long registerDebt(DebtCdo debtCdo) {
        Debt debt = new Debt(debtCdo);

        debt.setPaidDate(LocalDateTime.now());
        debt.setDebtPrice(debtCdo.getDebtPrice());

        return debtJpaStore.create(debt);
    }

    public long modifyDebt(DebtUdo debtUdo) {
        Debt debt = findDebtById(debtUdo.getId());

        debt.modifyAttributes(debtUdo);
        return debtJpaStore.update(debt);
    }

    public List<Debt> findDebts() {
        return debtJpaStore.retrieveAll();
    }

    public Debt findDebtById(long debtId) {
        Optional<Debt> debtOpt = debtJpaStore.retrieve(debtId);
        return debtOpt.orElseThrow(() -> new NotFoundException("외상이 존재하지 않습니다."));
    }

    public PageResponse<Debt> findDebtsByPage(Pageable pageable) {
        Page<Debt> page = debtJpaStore.retrieveAllByPage(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void removeDebt(long debtId) {
        debtJpaStore.delete(debtId);
    }

    public boolean isDebtExists(long debtId) {
        return debtJpaStore.exists(debtId);
    }
}
