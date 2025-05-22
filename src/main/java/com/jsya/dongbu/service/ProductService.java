package com.jsya.dongbu.service;

import com.jsya.dongbu.common.exception.NotFoundException;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.History;
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
//    private final HistoryService historyService;


    public String registerProduct(ProductCdo productCdo) {
        String productId = productCdo.genId();
        Product product = new Product(productCdo);

        product.setId(productId);
        return productJpaStore.create(product);
    }

    public String modifyProduct(ProductUdo productUdo) {
        Product product = findProductById(productUdo.getId());
        product.modifyAttributes(productUdo);

        // price 변경됐을경우 해당하는 History의 totalPrice도 변경
        if (product.getPrice() != productUdo.getPrice()) {
            List<Product> products = productJpaStore.retrieveListByHistory(product.getHistoryId());
            int newTotalPrice = products.stream().mapToInt(Product::getPrice).sum();

//            History history =  historyService.findHistoryById(product.getHistoryId());
//            history.setTotalPrice(newTotalPrice);
//            historyService.modifyHistory(history); // ✅ History update 반영
        }
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

    public PageResponse<ProductRdo> findProductsByPage(Pageable pageable) {
        Page<ProductRdo> page = productJpaStore.retrieveAllByPage(pageable)
                .map(ProductRdo::new);  // ✅ 올바른 방식으로 Page 내부 변환

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
    public List<ProductRdo> findProductsByHistoryId(String historyId) {
        List<Product> products = productJpaStore.retrieveListByHistory(historyId);
        return products.stream().map(ProductRdo::new).toList();
    }

    public PageResponse<Product> findProductsByMemberByPage(long memberId, Pageable pageable) {
        Page<Product> page = productJpaStore.retrieveListByMemberByPage(memberId, pageable);
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
