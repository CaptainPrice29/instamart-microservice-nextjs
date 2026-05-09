package com.instamart.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.instamart.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {
    // Custom method findByParams is inherited from ProductRepositoryCustom
}
