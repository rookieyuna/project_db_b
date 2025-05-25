package com.jsya.dongbu.model.sdo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtRdo {

    private long id; // 외상ID
    private int debtPrice; // 외상액
    private LocalDateTime registeredDate; // 최초 발생 날짜
    private LocalDateTime paidDate; // 갚은 날짜

    private long memberId;
}
