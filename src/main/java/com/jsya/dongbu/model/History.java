package com.jsya.dongbu.model;

import com.jsya.dongbu.model.sdo.HistoryCdo;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class History {

    @Id
    private String id; // 히스토리ID (memberId_startDate)
    private long startDate;
    private Long endDate;
    private Long bookDate; // 예약날짜
    private int prepaidPrice;
    private int totalPrice;

    private boolean debtYn;
    private boolean cardYn;

    private long memberId;

    public History(HistoryCdo historyCdo){
        BeanUtils.copyProperties(historyCdo, this);
    }

    public static String genId(long memberId, long startDate) {
        return memberId + "_" + startDate;
    }
}