package com.jsya.dongbu.model;

import com.jsya.dongbu.model.sdo.PaymentCdo;
import com.jsya.dongbu.model.sdo.PaymentUdo;
import com.jsya.dongbu.model.vo.PaymentMethod;
import com.jsya.dongbu.model.vo.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Payment {

    @Id
    private String id; // 결제ID (memberId_startDate_paymentDate)
    private LocalDateTime paymentDate; // 결제 날짜
    private int paymentPrice; // 지불액

    private PaymentMethod paymentMethod; // 지불방법 (현금,카드,이체)
    private PaymentType paymentType; // 지불유형(선불,후불,서비스)

    private long memberId; // 회원ID
    private String historyId; // 히스토리ID

    public Payment(PaymentCdo paymentCdo){
        BeanUtils.copyProperties(paymentCdo, this);
    }

    public static String genId(String historyId, LocalDateTime paymentDate) {
        return historyId + '_' + paymentDate;
    }

    public void modifyAttributes(PaymentUdo paymentUdo) {
        BeanUtils.copyProperties(paymentUdo, this);
    }
}