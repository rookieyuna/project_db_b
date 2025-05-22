package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.History;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryCdo {

    private LocalDateTime bookDate; // 예약날짜

    private long memberId;

    private ProductCdo[] productCdos;

    private PaymentCdo paymentCdo;

    public String genId(LocalDateTime startDate) {
        return History.genId(memberId, startDate);
    }
}
