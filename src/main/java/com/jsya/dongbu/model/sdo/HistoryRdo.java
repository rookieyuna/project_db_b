package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.Member;
import com.jsya.dongbu.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRdo {

    private String id; // 히스토리ID (memberId_startDate)
    private LocalDateTime startDate;// 맡긴날짜
    private LocalDateTime endDate; // 찾은날짜
    private LocalDateTime bookDate; // 예약날짜
    private int totalPrice;

    private boolean debtYn;

    private long memberId;
    private String memberAddress;
    private String memberName;

    private List<ProductRdo> products;
    private List<Payment> payments;

    public HistoryRdo(History history, Member member, List<ProductRdo> products, List<Payment> payments) {
        //
        BeanUtils.copyProperties(history, this);
        this.memberAddress = member.getAddress();
        this.memberName = member.getName();
        this.products = products;
        this.payments = payments;
    }
}
