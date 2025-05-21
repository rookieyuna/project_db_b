package com.jsya.dongbu.store.jpo;

import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.Product;
import com.jsya.dongbu.model.vo.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class ProductJpo {

    @Id
    private String id; // 제품ID (memberId_startDate_label)
    private int label;
    private OrderType orderType;
    private ProductType productType;
    @Column(length = 200, nullable = false)
    private String productEtc;
    private RepairType repairType;
    private TextureType textureType;
    private SizeType sizeType;
    private String color;
    private boolean premiumYn;
    private int price;

    @Column(nullable = true)
    private long memberId; // 회원ID
    @Column(length = 100, nullable = true)
    private String historyId; // 히스토리ID (memberId_startDate)

    public ProductJpo(Product product) {
        BeanUtils.copyProperties(product, this);
    }

    public Product toDomain() {
        Product product = new Product();
        BeanUtils.copyProperties(this, product);
        return product;
    }

    public static List<Product> toDomains(List<ProductJpo> productJpos) {
        return productJpos.stream().map(ProductJpo::toDomain).collect(Collectors.toList());
    }

    public static ProductJpo Sample() {
        return null;
    }
}