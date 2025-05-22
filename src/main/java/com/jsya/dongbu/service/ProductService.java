package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Product;
import com.jsya.dongbu.model.sdo.ProductCdo;
import com.jsya.dongbu.model.sdo.ProductRdo;
import com.jsya.dongbu.model.sdo.ProductUdo;
import com.jsya.dongbu.store.ProductJpaStore;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductJpaStore productJpaStore;

    public String registerProduct(ProductCdo productCdo) {
        String productId = productCdo.genId();
        Product product = new Product(productCdo);

        product.setId(productId);
        return productJpaStore.create(product);
    }

    public String modifyProduct(ProductUdo productUdo) {
        Product product = findProductById(productUdo.getId());
        product.modifyAttributes(productUdo);
        //TODO: price 변경됐을경우 해당하는 History totalPrice도 변경 로직 추가

        return productJpaStore.update(product);
    }

    public List<ProductRdo> findProducts() {
        List<Product> products = productJpaStore.retrieveAll();
        return products.stream().map(ProductRdo::new).toList();
    }

    public Product findProductById(String  productId) {
        Optional<Product> productOpt = productJpaStore.retrieve(productId);
        return productOpt.orElseThrow(() -> new NotFoundException("제품이 존재하지 않습니다."));
    }

    public PageResponse<Product> findProductsByPage(Pageable pageable) {
        Page<Product> page = productJpaStore.retrieveList(pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<Product> findProductsByHistory(String historyId, Pageable pageable) {
        Page<Product> page = productJpaStore.retrieveListByHistory(historyId, pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void removeProduct(String productId) {
        productJpaStore.delete(productId);
    }

    public boolean isProductExists(String productId) {
        return productJpaStore.exists(productId);
    }
}
