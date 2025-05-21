package com.jsya.dongbu.model;

import com.jsya.dongbu.model.sdo.HistoryCdo;
import com.jsya.dongbu.model.sdo.HistoryUdo;
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
    private Long startDate; // 맡긴날짜
    private Long endDate; // 찾은날짜
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

    public void modifyAttributes(HistoryUdo historyUdo) {
        BeanUtils.copyProperties(historyUdo, this);
    }
}