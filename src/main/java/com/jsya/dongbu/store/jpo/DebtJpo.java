package com.jsya.dongbu.store.jpo;

import com.jsya.dongbu.model.Debt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "debt")
public class DebtJpo {

    @Id
    private long id; // 외상ID
    private LocalDateTime registeredDate; // 최초 발생 날짜
    private LocalDateTime paidDate; // 갚은 날짜
    private int debtPrice; // 외상액

    @Column(nullable = false)
    private long memberId; // 회원ID

    public DebtJpo(Debt debt) {
        BeanUtils.copyProperties(debt, this);
    }

    public Debt toDomain() {
        Debt debt = new Debt();
        BeanUtils.copyProperties(this, debt);
        return debt;
    }

    public static List<Debt> toDomains(List<DebtJpo> debtJpos) {
        return debtJpos.stream().map(DebtJpo::toDomain).collect(Collectors.toList());
    }

    public static DebtJpo Sample() {
        return null;
    }
}