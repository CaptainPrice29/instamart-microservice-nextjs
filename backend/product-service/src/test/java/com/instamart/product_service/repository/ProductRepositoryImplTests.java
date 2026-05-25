package com.instamart.product_service.repository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.instamart.product_service.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTests {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
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
            .image("image1.jpg")
            .images(Arrays.asList("image1.jpg", "image2.jpg"))
            .tags(Arrays.asList("tag1", "tag2"))
            .features(Arrays.asList("feature1", "feature2"))
            .attributes(Collections.singletonMap("color", "red"))
            .rating(4.5)
            .ratingCount(100)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    @Test
    void testFindByParams_WithNullQueryParams() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = null;
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithEmptyQueryParams() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithNameFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "Test");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithBrandFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("brand", "TestBrand");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithCategoryFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("category", "Electronics");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithTagsFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("tags", "tag1");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithFeaturesFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("features", "feature1");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithExactMatchFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("price", "100.0");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithMultipleFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("category", "Electronics");
        queryParams.put("brand", "TestBrand");
        queryParams.put("name", "Test");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_WithPagination() {
        Pageable pageable = PageRequest.of(1, 5);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("category", "Electronics");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(15L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(1);
        assertThat(result.getSize()).isEqualTo(5);
        assertThat(result.getTotalElements()).isEqualTo(15L);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_EmptyResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("category", "NonExistent");

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(0L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(Collections.emptyList());

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isEqualTo(0L);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }

    @Test
    void testFindByParams_CaseInsensitiveSearch() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "TEST");
        List<Product> products = Arrays.asList(testProduct);

        when(mongoTemplate.count(any(Query.class), eq(Product.class))).thenReturn(1L);
        when(mongoTemplate.find(any(Query.class), eq(Product.class))).thenReturn(products);

        Page<Product> result = productRepository.findByParams(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mongoTemplate, times(1)).count(any(Query.class), eq(Product.class));
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Product.class));
    }
}
