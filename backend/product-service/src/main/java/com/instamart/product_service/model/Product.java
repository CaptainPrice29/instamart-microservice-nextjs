package com.instamart.product_service.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String productId;
    private String name;
    private String description;
    @Indexed(unique = true)
    private String sku;
    private Double price;
    private Double discountPrice;
    private String currency;
    private Integer stockQuantity;
    private Boolean isAvailable;
    private String category;
    private String brand;
    private String image; // Main thumbnail
    private List<String> images; // Gallery
    private List<String> tags;
    private List<String> features;
    private Map<String, String> attributes;
    private Double rating;
    private Integer ratingCount;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
