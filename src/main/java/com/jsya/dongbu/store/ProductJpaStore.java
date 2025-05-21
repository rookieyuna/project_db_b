package com.jsya.dongbu.store;

import com.jsya.dongbu.model.Product;
import com.jsya.dongbu.repository.ProductRepository;
import com.jsya.dongbu.store.impl.ProductStore;
import com.jsya.dongbu.store.jpo.ProductJpo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductJpaStore implements ProductStore {

    private final ProductRepository productRepository;

    @Override
    public String create(Product product) {
        ProductJpo productJpo = productRepository.save(new ProductJpo(product));
        return productJpo.getId();
    }

    @Override
    public List<String> createAll(List<Product> products) {
        return List.of();
    }

    @Override
    public String update(Product product) {
        ProductJpo productJpo = productRepository.save(new ProductJpo(product));
        return productJpo.getId();
    }

    @Override
    public Optional<Product> retrieve(String id) {
        Optional<ProductJpo> productJpo = productRepository.findById(id);
        return productJpo.map(ProductJpo::toDomain);
    }

    @Override
    public List<Product> retrieveAll() {
        List<ProductJpo> productJpos = productRepository.findAll();
        return ProductJpo.toDomains(productJpos);
    }

    @Override
    public Page<Product> retrieveList(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductJpo::toDomain);
    }

    @Override
    public Page<Product> retrieveListByHistory(String historyId, Pageable pageable) {
        Page<ProductJpo> page = productRepository.findByHistoryId(historyId, pageable);
        return page.map(ProductJpo::toDomain);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean exists(String id) {
        return productRepository.existsById(id);
    }
}