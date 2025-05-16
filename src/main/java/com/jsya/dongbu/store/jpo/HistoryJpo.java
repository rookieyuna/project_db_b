package com.jsya.dongbu.store.jpo;

import com.jsya.dongbu.model.History;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "history")
public class HistoryJpo {

    @Id
    private String id; // 히스토리ID (memberId_startDate)
    private Long startDate;
    private Long endDate;
    private Long bookDate; // 예약날짜
    private int prepaidPrice;
    private int totalPrice;

    private boolean debtYn;
    private boolean cardYn;

    @Column (nullable = false)
    private long memberId;


    public HistoryJpo(History history) {
        BeanUtils.copyProperties(history, this);
    }

    public History toDomain() {
        History history = new History();
        BeanUtils.copyProperties(this, history);
        return history;
    }

    public static List<History> toDomains(List<HistoryJpo> historyJpos) {
        return historyJpos.stream().map(HistoryJpo::toDomain).collect(Collectors.toList());
    }

    public static HistoryJpo Sample() {
        return new HistoryJpo("99_17171717", System.currentTimeMillis(), System.currentTimeMillis(), null, 0, 10000, true, true, 99L);
    }
}