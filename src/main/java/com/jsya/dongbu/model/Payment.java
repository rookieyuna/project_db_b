package com.jsya.dongbu.model;

import com.jsya.dongbu.model.sdo.PaymentCdo;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    private String id; // 결제ID (memberId_startDate_paymentDate)
    private long paymentDate; // 결제 날짜
    private int paymentPrice; // 지불액

    private String memberId; // 회원ID
    private String historyId; // 히스토리ID

    public Payment(PaymentCdo paymentCdo){
        BeanUtils.copyProperties(paymentCdo, this);
    }

    public static String genId(String historyId, long paymentDate) {
        return historyId + '_' + paymentDate;
    }
}