package com.jsya.dongbu.model.sdo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtUdo {

    private long id; // 외상ID
    private int debtPrice; // 외상액
    private LocalDateTime paidDate; // 갚은 날짜
}
