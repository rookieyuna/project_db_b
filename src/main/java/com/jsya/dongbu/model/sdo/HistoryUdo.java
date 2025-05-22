package com.jsya.dongbu.model.sdo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryUdo {

    private String id; // 히스토리ID
    private LocalDateTime startDate; // 맡긴날짜
    private LocalDateTime endDate; // 찾은날짜
    private LocalDateTime bookDate; // 예약날짜

    private boolean cardYn; // 카드여부
}
