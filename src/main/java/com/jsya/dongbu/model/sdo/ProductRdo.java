package com.jsya.dongbu.model.sdo;

import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.Member;
import com.jsya.dongbu.model.Product;
import com.jsya.dongbu.model.vo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRdo {

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

    public ProductRdo(Product product) {
        //
        BeanUtils.copyProperties(product, this);
    }
}
