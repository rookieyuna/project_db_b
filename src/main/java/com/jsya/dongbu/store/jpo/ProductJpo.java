package com.jsya.dongbu.store.jpo;

import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.Product;
import com.jsya.dongbu.model.vo.*;
import jakarta.persistence.*;
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
    @Column(nullable = false)

    private int label;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    @Column(length = 200, nullable = true)
    private String productEtc;
    @Enumerated(EnumType.STRING)
    private RepairType repairType;
    @Enumerated(EnumType.STRING)
    private TextureType textureType;
    @Enumerated(EnumType.STRING)
    private SizeType sizeType;
    private String color;
    private boolean premiumYn;
    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private long memberId; // 회원ID
    @Column(length = 100, nullable = false)
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