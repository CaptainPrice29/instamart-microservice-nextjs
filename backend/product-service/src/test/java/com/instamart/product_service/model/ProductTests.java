package com.instamart.product_service.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.Test;

class ProductTests {

    @Test
    void testProductBuilder_AllFields() {
        LocalDateTime now = LocalDateTime.now();
        Map<String, String> attributes = Collections.singletonMap("color", "red");
        List<String> images = Arrays.asList("image1.jpg", "image2.jpg");
        List<String> tags = Arrays.asList("tag1", "tag2");
        List<String> features = Arrays.asList("feature1", "feature2");

        Product product = Product.builder()
            .productId("prod-123")
            .name("Test Product")
            .description("Test Description")
            .sku("SKU-001")
            .price(100.0)
            .discountPrice(80.0)
            .currency("USD")
            .stockQuantity(50)
            .isAvailable(true)
            .category("Electronics")
            .brand("TestBrand")
            .image("main_image.jpg")
            .images(images)
            .tags(tags)
            .features(features)
            .attributes(attributes)
            .rating(4.5)
            .ratingCount(100)
            .createdAt(now)
            .updatedAt(now)
            .build();

        assertThat(product.getProductId()).isEqualTo("prod-123");
        assertThat(product.getName()).isEqualTo("Test Product");
        assertThat(product.getDescription()).isEqualTo("Test Description");
        assertThat(product.getSku()).isEqualTo("SKU-001");
        assertThat(product.getPrice()).isEqualTo(100.0);
        assertThat(product.getDiscountPrice()).isEqualTo(80.0);
        assertThat(product.getCurrency()).isEqualTo("USD");
        assertThat(product.getStockQuantity()).isEqualTo(50);
        assertThat(product.getIsAvailable()).isTrue();
        assertThat(product.getCategory()).isEqualTo("Electronics");
        assertThat(product.getBrand()).isEqualTo("TestBrand");
        assertThat(product.getImage()).isEqualTo("main_image.jpg");
        assertThat(product.getImages()).hasSize(2);
        assertThat(product.getTags()).hasSize(2);
        assertThat(product.getFeatures()).hasSize(2);
        assertThat(product.getAttributes()).hasSize(1);
        assertThat(product.getRating()).isEqualTo(4.5);
        assertThat(product.getRatingCount()).isEqualTo(100);
        assertThat(product.getCreatedAt()).isEqualTo(now);
        assertThat(product.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testProductBuilder_MinimalFields() {
        Product product = Product.builder()
            .name("Simple Product")
            .price(50.0)
            .build();

        assertThat(product.getName()).isEqualTo("Simple Product");
        assertThat(product.getPrice()).isEqualTo(50.0);
        assertThat(product.getProductId()).isNull();
        assertThat(product.getDescription()).isNull();
    }

    @Test
    void testProductBuilder_ToBuilder() {
        Product original = Product.builder()
            .productId("prod-123")
            .name("Original")
            .price(100.0)
            .build();

        Product modified = original.toBuilder()
            .name("Modified")
            .price(120.0)
            .build();

        assertThat(original.getName()).isEqualTo("Original");
        assertThat(original.getPrice()).isEqualTo(100.0);
        assertThat(modified.getProductId()).isEqualTo("prod-123");
        assertThat(modified.getName()).isEqualTo("Modified");
        assertThat(modified.getPrice()).isEqualTo(120.0);
    }

    @Test
    void testProductEqualsAndHashCode() {
        Product product1 = Product.builder()
            .productId("prod-123")
            .name("Test")
            .build();

        Product product2 = Product.builder()
            .productId("prod-123")
            .name("Test")
            .build();

        assertThat(product1).isEqualTo(product2);
        assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
    }

    @Test
    void testProductNotEquals() {
        Product product1 = Product.builder()
            .productId("prod-123")
            .name("Test1")
            .build();

        Product product2 = Product.builder()
            .productId("prod-124")
            .name("Test2")
            .build();

        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void testProductNoArgsConstructor() {
        Product product = new Product();

        assertThat(product.getProductId()).isNull();
        assertThat(product.getName()).isNull();
        assertThat(product.getPrice()).isNull();
    }

    @Test
    void testProductAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();

        Product product = new Product(
            "prod-123",
            "Test Product",
            "Test Description",
            "SKU-001",
            100.0,
            80.0,
            "USD",
            50,
            true,
            "Electronics",
            "TestBrand",
            "image.jpg",
            Arrays.asList("img1.jpg"),
            Arrays.asList("tag1"),
            Arrays.asList("feature1"),
            Collections.singletonMap("color", "red"),
            4.5,
            100,
            now,
            now
        );

        assertThat(product.getProductId()).isEqualTo("prod-123");
        assertThat(product.getName()).isEqualTo("Test Product");
        assertThat(product.getPrice()).isEqualTo(100.0);
    }

    @Test
    void testProductSettersAndGetters() {
        Product product = new Product();

        product.setProductId("prod-123");
        product.setName("Test");
        product.setPrice(100.0);
        product.setStockQuantity(50);
        product.setIsAvailable(true);

        assertThat(product.getProductId()).isEqualTo("prod-123");
        assertThat(product.getName()).isEqualTo("Test");
        assertThat(product.getPrice()).isEqualTo(100.0);
        assertThat(product.getStockQuantity()).isEqualTo(50);
        assertThat(product.getIsAvailable()).isTrue();
    }

    @Test
    void testProductWithNullCollections() {
        Product product = Product.builder()
            .productId("prod-123")
            .name("Test")
            .images(null)
            .tags(null)
            .features(null)
            .attributes(null)
            .build();

        assertThat(product.getImages()).isNull();
        assertThat(product.getTags()).isNull();
        assertThat(product.getFeatures()).isNull();
        assertThat(product.getAttributes()).isNull();
    }

    @Test
    void testProductWithEmptyCollections() {
        Product product = Product.builder()
            .productId("prod-123")
            .name("Test")
            .images(Collections.emptyList())
            .tags(Collections.emptyList())
            .features(Collections.emptyList())
            .attributes(Collections.emptyMap())
            .build();

        assertThat(product.getImages()).isEmpty();
        assertThat(product.getTags()).isEmpty();
        assertThat(product.getFeatures()).isEmpty();
        assertThat(product.getAttributes()).isEmpty();
    }

    @Test
    void testProductToString() {
        Product product = Product.builder()
            .productId("prod-123")
            .name("Test Product")
            .build();

        String toString = product.toString();

        assertThat(toString).contains("productId");
        assertThat(toString).contains("prod-123");
        assertThat(toString).contains("Test Product");
    }

    @Test
    void testProductWithSpecialCharacters() {
        Product product = Product.builder()
            .productId("prod-123")
            .name("Test & Product")
            .description("Special chars: @#$%^&*()")
            .brand("Brand-Name_123")
            .build();

        assertThat(product.getName()).isEqualTo("Test & Product");
        assertThat(product.getDescription()).contains("@#$%^&*()");
        assertThat(product.getBrand()).isEqualTo("Brand-Name_123");
    }

    @Test
    void testProductWithLargeNumbers() {
        Product product = Product.builder()
            .productId("prod-123")
            .price(999999.99)
            .discountPrice(555555.50)
            .stockQuantity(1000000)
            .ratingCount(1000000)
            .rating(5.0)
            .build();

        assertThat(product.getPrice()).isEqualTo(999999.99);
        assertThat(product.getDiscountPrice()).isEqualTo(555555.50);
        assertThat(product.getStockQuantity()).isEqualTo(1000000);
        assertThat(product.getRatingCount()).isEqualTo(1000000);
    }
}
