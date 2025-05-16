package com.jsya.dongbu.store;

import com.jsya.dongbu.model.History;
import com.jsya.dongbu.repository.HistoryRepository;
import com.jsya.dongbu.store.impl.HistoryStore;
import com.jsya.dongbu.store.jpo.HistoryJpo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HistoryJpaStore implements HistoryStore {

    private final HistoryRepository historyRepository;

    @Override
    public String create(History history) {
        HistoryJpo historyJpo = historyRepository.save(new HistoryJpo(history));
        return historyJpo.getId();
    }

    @Override
    public List<String> createAll(List<History> historys) {
        List<HistoryJpo> jpos = historys.stream()
                .map(HistoryJpo::new)
                .toList();
        return historyRepository.saveAll(jpos).stream()
                .map(HistoryJpo::getId)
                .toList();
    }

    @Override
    public String update(History history) {
        HistoryJpo historyJpo = historyRepository.save(new HistoryJpo(history));
        return historyJpo.getId();
    }

    @Override
    public Optional<History> retrieve(String id) {
        Optional<HistoryJpo> historyJpo = historyRepository.findById(id);
        return historyJpo.map(HistoryJpo::toDomain);
    }

    @Override
    public List<History> retrieveAll() {
        List<HistoryJpo> historyJpos = historyRepository.findAll();
        return HistoryJpo.toDomains(historyJpos);
    }

    @Override
    public Page<History> retrieveList(Pageable pageable) {
        return historyRepository.findAll(pageable).map(HistoryJpo::toDomain);
    }

    @Override
    public Page<History> retrieveListByMember(long memberId, Pageable pageable) {
        Page<HistoryJpo> page = historyRepository.findByMemberId(memberId, pageable);
        return page.map(HistoryJpo::toDomain);
    }

    @Override
    public void delete(String id) {
        historyRepository.deleteById(id);
    }

    @Override
    public boolean exists(String id) {
        return historyRepository.existsById(id);
    }
}
