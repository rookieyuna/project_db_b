package com.jsya.dongbu.store;

import com.jsya.dongbu.model.Debt;
import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.repository.DebtRepository;
import com.jsya.dongbu.repository.PaymentRepository;
import com.jsya.dongbu.store.impl.DebtStore;
import com.jsya.dongbu.store.impl.PaymentStore;
import com.jsya.dongbu.store.jpo.DebtJpo;
import com.jsya.dongbu.store.jpo.PaymentJpo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DebtJpaStore implements DebtStore {

    private final DebtRepository debtRepository;

    @Override
    public long create(Debt debt) {
        DebtJpo debtJpo = debtRepository.save(new DebtJpo(debt));
        return debtJpo.getId();
    }

    @Override
    public long update(Debt debt) {
        DebtJpo debtJpo = debtRepository.save(new DebtJpo(debt));
        return debtJpo.getId();
    }

    @Override
    public Optional<Debt> retrieve(long id) {
        Optional<DebtJpo> debtJpo = debtRepository.findById(id);
        return debtJpo.map(DebtJpo::toDomain);
    }

    @Override
    public List<Debt> retrieveAll() {
        List<DebtJpo> debtJpos = debtRepository.findAll();
        return DebtJpo.toDomains(debtJpos);
    }

    @Override
    public Page<Debt> retrieveAllByPage(Pageable pageable) {
        return debtRepository.findAll(pageable).map(DebtJpo::toDomain);
    }

    @Override
    public Page<Debt> retrieveListByMemberByPage(long memberId, Pageable pageable) {
        Page<DebtJpo> page = debtRepository.findByMemberId(memberId, pageable);
        return page.map(DebtJpo::toDomain);
    }

    @Override
    public void delete(long id) {
        debtRepository.deleteById(id);
    }

    @Override
    public boolean exists(long id) {
        return debtRepository.existsById(id);
    }
}