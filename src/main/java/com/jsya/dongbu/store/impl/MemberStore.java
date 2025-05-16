package com.jsya.dongbu.store.impl;

import com.jsya.dongbu.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberStore {

    long create(Member member);
    List<Long> createAll(List<Member> members);
    long update(Member member);
    Optional<Member> retrieve(long id);
    List<Member> retrieveAll();
    Page<Member> retrieveList(Pageable pageable);
    Page<Member> retrieveListByAddress(String addressKeyword, Pageable pageable);
    void delete(long id);
    boolean exists(long id);
}
