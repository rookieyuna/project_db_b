package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCdo {

    private String address;
    private String name;
    private String phone;

    public String genId() {
        return Member.genId();
    }
}
