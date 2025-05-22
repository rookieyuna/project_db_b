package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.History;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryCdo {

    private Long bookDate; // 예약날짜
    private boolean cardYn; // 카드여부

    private long memberId;

    private ProductCdo[] productCdos;

    private PaymentCdo paymentCdo;

    public String genId(long startDate) {
        return History.genId(memberId, startDate);
    }
}
