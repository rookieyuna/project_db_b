package com.jsya.dongbu.model.sdo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCdo {

    private long paymentDate; // 갚은 날짜
    private int paymentPrice; // 지불액

    private String memberId; // 회원ID
    private String historyId; // 히스토리ID
}
