package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.Debt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtCdo {
    private int debtPrice; // 외상액
    private LocalDateTime registeredDate; // 최초 발생 날짜
    private LocalDateTime paidDate; // 갚은 날짜

    private long memberId;

    public String genId() {
        return Debt.genId();
    }
}
