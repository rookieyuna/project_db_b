package com.jsya.dongbu.store.jpo;

import com.jsya.dongbu.model.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class MemberJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column (length = 200, nullable = false)
    private String address;
    @Column(length = 20, nullable = true)
    private String name;
    @Column(length = 20, nullable = true)
    private String phone;
    private boolean status;
    private long registeredDate;

    public MemberJpo(Member member) {
        BeanUtils.copyProperties(member, this);
    }

    public Member toDomain() {
        Member member = new Member();
        BeanUtils.copyProperties(this, member);
        return member;
    }

    public static List<Member> toDomains(List<MemberJpo> memberJpos) {
        return memberJpos.stream().map(MemberJpo::toDomain).collect(Collectors.toList());
    }

    public static MemberJpo Sample() {
        return new MemberJpo(99,"가산동 112", "홍길동","010-1234-5678", true, System.currentTimeMillis());
    }
}