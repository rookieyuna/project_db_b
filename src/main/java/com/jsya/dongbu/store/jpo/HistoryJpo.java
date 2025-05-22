package com.jsya.dongbu.store.jpo;

import com.jsya.dongbu.model.History;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime bookDate; // 예약날짜
    private int totalPrice;

    private boolean debtYn;

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
        return new HistoryJpo("99_17171717", LocalDateTime.now(), LocalDateTime.now(), null, 0, true, 99L);
    }
}