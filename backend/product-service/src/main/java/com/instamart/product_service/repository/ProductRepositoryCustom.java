package com.instamart.product_service.repository;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.instamart.product_service.model.Product;

public interface ProductRepositoryCustom {
    Page<Product> findByParams(Map<String, String> queryParams, Pageable pageable);
}
