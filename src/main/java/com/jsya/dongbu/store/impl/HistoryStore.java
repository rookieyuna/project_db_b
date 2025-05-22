package com.jsya.dongbu.store.impl;

import com.jsya.dongbu.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HistoryStore {

    String create(History history);
    String update(History history);
    Optional<History> retrieve(String id);
    List<History> retrieveAll();
    Page<History> retrieveAllByPage(Pageable pageable);
    Page<History> retrieveListByMemberByPage(long memberId, Pageable pageable);
    void delete(String id);
    boolean exists(String id);
}
