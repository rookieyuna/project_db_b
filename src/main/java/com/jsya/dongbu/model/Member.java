package com.jsya.dongbu.model;

import com.jsya.dongbu.model.sdo.MemberCdo;
import com.jsya.dongbu.model.sdo.MemberUdo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    private long id; // 회원ID
    private String address;
    private String name;
    private String phone;
    private boolean status; // 가용상태
    private long registeredDate; // 가입일

    public Member(MemberCdo memberCdo){
        BeanUtils.copyProperties(memberCdo, this);
    }

    public static String genId() {
        return UUID.randomUUID().toString();
    }

    public void modifyAttributes(MemberUdo memberUdo) {
        BeanUtils.copyProperties(memberUdo, this);
    }
}