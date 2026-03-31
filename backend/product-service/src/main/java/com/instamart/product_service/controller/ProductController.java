package com.instamart.product_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instamart.product_service.model.Product;
import com.instamart.product_service.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        System.out.println("Product added successfully");

        productService.createProduct(product);
        return ResponseEntity.ok(product.toBuilder().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // paginated products with query params filters
    // @GetMapping
    // public ResponseEntity<Page<Product>> getProducts(@RequestParam(defaultValue =
    // "0") int page,
    // @RequestParam(defaultValue = "10") int size) {
    // Pageable pageable = PageRequest.of(page, size);
    // return ResponseEntity.ok(productService.getFilteredProducts(pageable));
    // }
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) Map<String, String> queryParams,
            Pageable pageable) {
        System.out.println("queryParams: " + queryParams);
        System.out.println("pageable: " + pageable);
        return ResponseEntity.ok(productService.getFilteredProducts(queryParams, pageable));
    }

}
