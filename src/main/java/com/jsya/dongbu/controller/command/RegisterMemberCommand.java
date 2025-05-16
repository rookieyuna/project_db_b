package com.jsya.dongbu.controller.command;

import com.jsya.dongbu.model.sdo.MemberCdo;
import com.jsya.dongbu.common.shared.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMemberCommand {

    private MemberCdo memberCdo;

    public void validate() {
        Assert.notNull(memberCdo, String.format(MessageConstant.REQUIRED, "memberCdo"));
    }
}
