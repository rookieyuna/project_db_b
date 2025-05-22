package com.jsya.dongbu.controller;

import com.jsya.dongbu.common.response.ApiResponse;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Product;
import com.jsya.dongbu.model.sdo.ProductCdo;
import com.jsya.dongbu.model.sdo.ProductRdo;
import com.jsya.dongbu.model.sdo.ProductUdo;
import com.jsya.dongbu.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ApiResponse<String>  registerProduct(@RequestBody ProductCdo productCdo) {
        String id = productService.registerProduct(productCdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/modify")
    public ApiResponse<String> modifyProduct(@RequestBody ProductUdo productUdo) {
        String id = productService.modifyProduct(productUdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/remove")
    public ApiResponse<String> removeProduct(@RequestBody String productId) {
        productService.removeProduct(productId);
        return ApiResponse.ok(productId);
    }

    @PostMapping("/find-by-id")
    public ApiResponse<Product>  findProductById(@RequestBody String productId) {
        Product product = productService.findProductById(productId);
        return ApiResponse.ok(product);
    }

    @PostMapping("/find-all")
    public ApiResponse<List<ProductRdo>>  findProducts() {
        List<ProductRdo> products = productService.findProducts();
        return ApiResponse.ok(products);
    }

    @GetMapping("/find-by-page")
    public ApiResponse<PageResponse<Product>> findProductsByPage(Pageable pageable) {
        return ApiResponse.ok(productService.findProductsByPage(pageable));
    }

    @GetMapping("/find-by-history-by-page")
    public ApiResponse<PageResponse<Product>> findProductsByHistoryByPage(
            @RequestParam String historyId,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ApiResponse.ok(productService.findProductsByHistory(historyId, pageable));
    }
}
