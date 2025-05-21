package com.jsya.dongbu.repository;

import com.jsya.dongbu.store.jpo.MemberJpo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberJpo, Long> {
    List<MemberJpo> findByNameContains(String name);
    Page<MemberJpo> findByAddressContains(String address, Pageable pageable);
}
