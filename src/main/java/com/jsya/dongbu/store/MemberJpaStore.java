package com.jsya.dongbu.store;

import com.jsya.dongbu.model.Member;
import com.jsya.dongbu.store.impl.MemberStore;
import com.jsya.dongbu.store.jpo.MemberJpo;
import com.jsya.dongbu.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MemberJpaStore implements MemberStore {

    private final MemberRepository memberRepository;

    @Override
    public long create(Member member) {
        MemberJpo memberJpo = memberRepository.save(new MemberJpo(member));
        return memberJpo.getId();
    }

    @Override
    public List<Long> createAll(List<Member> members) {
        List<MemberJpo> jpos = members.stream()
                .map(MemberJpo::new)
                .toList();
        return memberRepository.saveAll(jpos).stream()
                .map(MemberJpo::getId)
                .toList();
    }

    @Override
    public long update(Member member) {
        MemberJpo memberJpo = memberRepository.save(new MemberJpo(member));
        return memberJpo.getId();
    }

    @Override
    public Optional<Member> retrieve(long id) {
        Optional<MemberJpo> memberJpo = memberRepository.findById(id);
        return memberJpo.map(MemberJpo::toDomain);
    }

    @Override
    public List<Member> retrieveAll() {
        List<MemberJpo> memberJpos = memberRepository.findAll();
        return MemberJpo.toDomains(memberJpos);
    }

    @Override
    public Page<Member> retrieveList(Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberJpo::toDomain);
    }

    @Override
    public Page<Member> retrieveListByAddress(String addressKeyword, Pageable pageable) {
        Page<MemberJpo> page = memberRepository.findByAddressContains(addressKeyword, pageable);
        return page.map(MemberJpo::toDomain);
    }

    @Override
    public void delete(long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public boolean exists(long id) {
        return memberRepository.existsById(id);
    }
}
