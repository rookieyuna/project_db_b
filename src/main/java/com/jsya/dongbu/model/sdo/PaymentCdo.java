package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCdo {

    private LocalDateTime paymentDate; // 갚은 날짜
    private int paymentPrice; // 지불액

    private long memberId; // 회원ID
    private String historyId; // 히스토리ID

    public String genId() {
        return Payment.genId(historyId, paymentDate);
    }
}
