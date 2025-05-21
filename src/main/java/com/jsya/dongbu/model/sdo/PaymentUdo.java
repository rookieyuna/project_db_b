package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUdo {

    private String id; // 결제ID (memberId_startDate_paymentDate)
    private long paymentDate; // 갚은 날짜
    private int paymentPrice; // 지불액
}
