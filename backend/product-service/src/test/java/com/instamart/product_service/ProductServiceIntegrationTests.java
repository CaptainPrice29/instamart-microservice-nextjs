package com.instamart.product_service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.instamart.product_service.model.Product;
import com.instamart.product_service.repository.ProductRepository;
import com.instamart.product_service.repository.ProductRepositoryImpl;

@DataMongoTest
@ContextConfiguration(classes = {ProductRepository.class, ProductRepositoryImpl.class})
class ProductServiceIntegrationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct1;
    private Product testProduct2;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("products");

        LocalDateTime now = LocalDateTime.now();

        testProduct1 = Product.builder()
            .productId("prod-001")
            .name("Laptop")
            .description("High performance laptop")
            .sku("SKU-LAPTOP-001")
            .price(1000.0)
            .discountPrice(800.0)
            .currency("USD")
            .stockQuantity(50)
            .isAvailable(true)
            .category("Electronics")
            .brand("Dell")
            .image("laptop.jpg")
            .images(Arrays.asList("laptop1.jpg", "laptop2.jpg"))
            .tags(Arrays.asList("electronics", "computers"))
            .features(Arrays.asList("16GB RAM", "512GB SSD"))
            .attributes(Collections.singletonMap("processor", "Intel i7"))
            .rating(4.8)
            .ratingCount(250)
            .createdAt(now)
            .updatedAt(now)
            .build();

        testProduct2 = Product.builder()
            .productId("prod-002")
            .name("Mouse")
            .description("Wireless mouse")
            .sku("SKU-MOUSE-001")
            .price(50.0)
            .discountPrice(40.0)
            .currency("USD")
            .stockQuantity(200)
            .isAvailable(true)
            .category("Accessories")
            .brand("Logitech")
            .image("mouse.jpg")
            .images(Arrays.asList("mouse1.jpg"))
            .tags(Arrays.asList("accessories", "input"))
            .features(Arrays.asList("Wireless", "Ergonomic"))
            .attributes(Collections.singletonMap("color", "black"))
            .rating(4.5)
            .ratingCount(150)
            .createdAt(now)
            .updatedAt(now)
            .build();
    }

    @Test
    void testCreateProduct_Integration() {
        Product created = productRepository.save(testProduct1);

        assertThat(created).isNotNull();
        assertThat(created.getProductId()).isEqualTo("prod-001");
        assertThat(created.getName()).isEqualTo("Laptop");
        assertThat(created.getPrice()).isEqualTo(1000.0);
    }

    @Test
    void testGetProductById_Integration() {
        productRepository.save(testProduct1);

        Optional<Product> retrieved = productRepository.findById("prod-001");

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getName()).isEqualTo("Laptop");
    }

    @Test
    void testGetProductById_NotFound() {
        Optional<Product> retrieved = productRepository.findById("non-existent");

        assertThat(retrieved).isEmpty();
    }

    @Test
    void testGetAllProducts_Integration() {
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(2);
    }

    @Test
    void testUpdateProduct_Integration() {
        productRepository.save(testProduct1);

        testProduct1.setPrice(900.0);
        testProduct1.setStockQuantity(40);

        Product updated = productRepository.save(testProduct1);

        assertThat(updated.getPrice()).isEqualTo(900.0);
        assertThat(updated.getStockQuantity()).isEqualTo(40);
    }

    @Test
    void testDeleteProduct_Integration() {
        productRepository.save(testProduct1);

        productRepository.deleteById("prod-001");

        Optional<Product> retrieved = productRepository.findById("prod-001");
        assertThat(retrieved).isEmpty();
    }

    @Test
    void testFilterByName_Integration() {
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);

        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> filters = Collections.singletonMap("name", "Laptop");

        Page<Product> results = productRepository.findByParams(filters, pageable);

        assertThat(results.getContent()).hasSize(1);
        assertThat(results.getContent().get(0).getName()).isEqualTo("Laptop");
    }

    @Test
    void testFilterByCategory_Integration() {
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);

        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> filters = Collections.singletonMap("category", "Electronics");

        Page<Product> results = productRepository.findByParams(filters, pageable);

        assertThat(results.getContent()).hasSize(1);
        assertThat(results.getContent().get(0).getCategory()).isEqualTo("Electronics");
    }

    @Test
    void testFilterByBrand_Integration() {
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);

        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> filters = Collections.singletonMap("brand", "Logitech");

        Page<Product> results = productRepository.findByParams(filters, pageable);

        assertThat(results.getContent()).hasSize(1);
        assertThat(results.getContent().get(0).getBrand()).isEqualTo("Logitech");
    }

    @Test
    void testFilterByMultipleCriteria_Integration() {
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);

        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> filters = new HashMap<>();
        filters.put("category", "Electronics");
        filters.put("brand", "Dell");

        Page<Product> results = productRepository.findByParams(filters, pageable);

        assertThat(results.getContent()).hasSize(1);
        assertThat(results.getContent().get(0).getProductId()).isEqualTo("prod-001");
    }

    @Test
    void testCaseInsensitiveSearch_Integration() {
        productRepository.save(testProduct1);

        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> filters = Collections.singletonMap("name", "laptop");

        Page<Product> results = productRepository.findByParams(filters, pageable);

        assertThat(results.getContent()).hasSize(1);
    }

    @Test
    void testPartialMatch_Integration() {
        productRepository.save(testProduct1);

        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> filters = Collections.singletonMap("name", "Lap");

        Page<Product> results = productRepository.findByParams(filters, pageable);

        assertThat(results.getContent()).hasSize(1);
    }

    @Test
    void testPagination_Integration() {
        for (int i = 0; i < 25; i++) {
            Product product = testProduct1.toBuilder()
                .productId("prod-" + i)
                .name("Product " + i)
                .build();
            productRepository.save(product);
        }

        Pageable page1 = PageRequest.of(0, 10);
        Page<Product> results1 = productRepository.findByParams(new HashMap<>(), page1);

        assertThat(results1.getTotalElements()).isEqualTo(25);
        assertThat(results1.getContent()).hasSize(10);
        assertThat(results1.getNumber()).isEqualTo(0);

        Pageable page2 = PageRequest.of(1, 10);
        Page<Product> results2 = productRepository.findByParams(new HashMap<>(), page2);

        assertThat(results2.getContent()).hasSize(10);
        assertThat(results2.getNumber()).isEqualTo(1);
    }

    @Test
    void testNoFilters_ReturnsAll_Integration() {
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> results = productRepository.findByParams(new HashMap<>(), pageable);

        assertThat(results.getTotalElements()).isEqualTo(2);
    }

    @Test
    void testNonMatchingFilter_Integration() {
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);

        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> filters = Collections.singletonMap("category", "NonExistent");

        Page<Product> results = productRepository.findByParams(filters, pageable);

        assertThat(results.getContent()).isEmpty();
        assertThat(results.getTotalElements()).isEqualTo(0);
    }

    @Test
    void testCreateAndRetrieve_Integration() {
        Product created = productRepository.save(testProduct1);

        assertThat(created.getProductId()).isNotNull();

        Optional<Product> retrieved = productRepository.findById(created.getProductId());

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get()).isEqualTo(created);
    }

    @Test
    void testUpdateAndVerify_Integration() {
        productRepository.save(testProduct1);

        testProduct1.setDescription("Updated description");
        testProduct1.setRating(4.9);

        productRepository.save(testProduct1);

        Optional<Product> retrieved = productRepository.findById("prod-001");

        assertThat(retrieved.get().getDescription()).isEqualTo("Updated description");
        assertThat(retrieved.get().getRating()).isEqualTo(4.9);
    }

    @Test
    void testMultipleOperations_Integration() {
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(2);

        Optional<Product> first = productRepository.findById("prod-001");
        assertThat(first).isPresent();

        productRepository.deleteById("prod-002");

        List<Product> remaining = productRepository.findAll();
        assertThat(remaining).hasSize(1);
    }
}
