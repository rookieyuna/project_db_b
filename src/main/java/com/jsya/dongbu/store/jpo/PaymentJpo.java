package com.jsya.dongbu.store.jpo;

import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.model.vo.PaymentMethod;
import com.jsya.dongbu.model.vo.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentJpo {

    @Id
    private String id; // 결제ID (memberId_startDate_paymentDate)
    private LocalDateTime paymentDate; // 결제 날짜
    private int paymentPrice; // 지불액

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // 지불방법 (현금,카드,이체)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; // 지불유형(선불,후불,서비스)

    @Column(nullable = false)
    private long memberId; // 회원ID
    @Column(length = 100, nullable = false)
    private String historyId; // 히스토리ID

    public PaymentJpo(Payment payment) {
        BeanUtils.copyProperties(payment, this);
    }

    public Payment toDomain() {
        Payment payment = new Payment();
        BeanUtils.copyProperties(this, payment);
        return payment;
    }

    public static List<Payment> toDomains(List<PaymentJpo> paymentJpos) {
        return paymentJpos.stream().map(PaymentJpo::toDomain).collect(Collectors.toList());
    }

    public static PaymentJpo Sample() {
        return null;
    }
}