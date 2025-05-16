package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUdo {

    private long id;
    private String address;
    private String name;
    private String phone;
    private boolean status;
}
