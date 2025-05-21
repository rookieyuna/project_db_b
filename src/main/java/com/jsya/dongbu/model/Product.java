package com.jsya.dongbu.model;

import com.jsya.dongbu.model.sdo.HistoryUdo;
import com.jsya.dongbu.model.sdo.ProductCdo;
import com.jsya.dongbu.model.sdo.ProductUdo;
import com.jsya.dongbu.model.vo.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    private String id; // 제품ID (memberId_startDate_label)
    private int label;
    private OrderType orderType;
    private ProductType productType;
    private String productEtc;
    private RepairType repairType;
    private TextureType textureType;
    private SizeType sizeType;
    private String color;
    private boolean premiumYn;
    private int price;

    private long memberId;
    private String historyId;

    public Product(ProductCdo productCdo){
        BeanUtils.copyProperties(productCdo, this);
    }

    public static String genId(String historyId, int label) {
        return historyId + '_' + label;
    }

    public void modifyAttributes(ProductUdo productUdo) {
        BeanUtils.copyProperties(productUdo, this);
    }
}