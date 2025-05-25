package com.jsya.dongbu.model;

import com.jsya.dongbu.model.sdo.DebtCdo;
import com.jsya.dongbu.model.sdo.DebtUdo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Debt {

    @Id
    private long id; // 외상ID
    private int debtPrice; // 외상액
    private LocalDateTime registeredDate; // 최초 발생 날짜
    private LocalDateTime paidDate; // 갚은 날짜

    private long memberId;

    public Debt(DebtCdo debtCdo){
        BeanUtils.copyProperties(debtCdo, this);
    }

    public static String genId() {
        return UUID.randomUUID().toString();
    }

    public void modifyAttributes(DebtUdo debtUdo) {
        BeanUtils.copyProperties(debtUdo, this);
    }
}