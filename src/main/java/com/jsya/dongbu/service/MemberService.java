package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Member;
import com.jsya.dongbu.model.sdo.MemberCdo;
import com.jsya.dongbu.model.sdo.MemberUdo;
import com.jsya.dongbu.store.MemberJpaStore;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberJpaStore memberJpaStore;

    public long registerMember(MemberCdo memberCdo) {
        Member member = new Member(memberCdo);

        member.setRegisteredDate(System.currentTimeMillis());
        member.setStatus(true);

        return memberJpaStore.create(member);
    }

    public long modifyMember(MemberUdo memberUdo) {
        Member member = findMemberById(memberUdo.getId());

        member.modifyAttributes(memberUdo);
        return memberJpaStore.update(member);
    }

    public List<Member> findMembers() {
        return memberJpaStore.retrieveAll();
    }

    public Member findMemberById(long memberId) {
        Optional<Member> memberOpt = memberJpaStore.retrieve(memberId);
        return memberOpt.orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다."));
    }

    public PageResponse<Member> findMembersByPage(Pageable pageable) {
        Page<Member> page = memberJpaStore.retrieveList(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<Member> findMembersByAddress(String address, Pageable pageable) {
        Page<Member> page = memberJpaStore.retrieveListByAddress(address, pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void removeMember(long memberId) {
        memberJpaStore.delete(memberId);
    }

    public boolean isMemberExists(long memberId) {
        return memberJpaStore.exists(memberId);
    }
}
