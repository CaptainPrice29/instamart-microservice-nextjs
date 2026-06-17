package com.instamart.product_service.repository;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.instamart.product_service.model.Product;
import org.springframework.data.domain.Sort;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Product> findByParams(Map<String, String> queryParams, Pageable pageable) {
        Query query = new Query();

        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                // Enable fuzzy search (case-insensitive regex) for specific text fields
                if (key.matches("name|brand|category|tags|features")) {
                    query.addCriteria(Criteria.where(key).regex(value, "i"));
                } else {
                    query.addCriteria(Criteria.where(key).is(value));
                }
                // if (key.equals("name") || key.equals("brand") || key.equals("category")
                // || key.equals("tags") || key.equals("features")) {
                // query.addCriteria(Criteria.where(key).regex(value, "i"));
                // } else {
                // // Exact match for other fields (e.g., attributes.color, etc.)
                // query.addCriteria(Criteria.where(key).is(value));
                // }
            }
        }

        long total = mongoTemplate.count(query, Product.class);
        query.with(pageable);
        List<Product> products = mongoTemplate.find(query, Product.class);

        return new PageImpl<>(products, pageable, total);
    }
}
