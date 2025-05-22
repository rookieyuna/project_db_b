package com.jsya.dongbu.controller;

import com.jsya.dongbu.common.response.ApiResponse;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Member;
import com.jsya.dongbu.model.sdo.MemberCdo;
import com.jsya.dongbu.model.sdo.MemberUdo;
import com.jsya.dongbu.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ApiResponse<Long>  registerMember(@RequestBody MemberCdo memberCdo) {
        long id = memberService.registerMember(memberCdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/modify")
    public ApiResponse<Long> modifyMember(@RequestBody MemberUdo memberUdo) {
        long id = memberService.modifyMember(memberUdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/remove")
    public ApiResponse<Long> removeMember(@RequestBody long memberId) {
        memberService.removeMember(memberId);
        return ApiResponse.ok(memberId);
    }

    @PostMapping("/find-by-id")
    public ApiResponse<Member>  findMemberById(@RequestBody long memberId) {
        Member member = memberService.findMemberById(memberId);
        return ApiResponse.ok(member);
    }

    @PostMapping("/find-all")
    public ApiResponse<List<Member>>  findMembers() {
        List<Member> members = memberService.findMembers();
        return ApiResponse.ok(members);
    }

    @GetMapping("/find-by-page")
    public ApiResponse<PageResponse<Member>> findMembersByPage(Pageable pageable) {
        return ApiResponse.ok(memberService.findMembersByPage(pageable));
    }

    @GetMapping("/find-by-address-by-page")
    public ApiResponse<PageResponse<Member>> findMembersByAddressByPage(
            @RequestParam String address,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ApiResponse.ok(memberService.findMembersByAddress(address, pageable));
    }
}
