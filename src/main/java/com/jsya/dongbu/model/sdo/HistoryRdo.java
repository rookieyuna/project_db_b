package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRdo {

    private String id; // 히스토리ID (memberId_startDate)
    private Long startDate;// 맡긴날짜
    private Long endDate; // 찾은날짜
    private Long bookDate; // 예약날짜
    private int prepaidPrice;
    private int totalPrice;

    private boolean debtYn;
    private boolean cardYn;

    private long memberId;
    private String memberAddress;
    private String memberName;

    public HistoryRdo(History history, Member member) {
        //
        BeanUtils.copyProperties(history, this);
        this.memberAddress = member.getAddress();
        this.memberName = member.getName();
    }
}
