package com.jsya.dongbu.repository;

import com.jsya.dongbu.store.jpo.MemberJpo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberJpo, Long> {
    Page<MemberJpo> findByNameContains(String name, Pageable pageable);
    Page<MemberJpo> findByAddressContains(String address, Pageable pageable);
}
