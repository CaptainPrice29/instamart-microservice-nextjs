package com.instamart.product_service.service;

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

import com.instamart.product_service.model.Product;
import com.instamart.product_service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

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
    void testCreateProduct_Success() {
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        Product result = productService.createProduct(testProduct);

        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo("prod-123");
        assertThat(result.getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void testUpdateProduct_Success() {
        String productId = "prod-123";
        Product updatedData = Product.builder()
            .name("Updated Product")
            .description("Updated Description")
            .price(120.0)
            .discountPrice(90.0)
            .currency("EUR")
            .stockQuantity(60)
            .isAvailable(false)
            .category("Home")
            .brand("NewBrand")
            .image("new_image.jpg")
            .images(Arrays.asList("new_image.jpg"))
            .tags(Arrays.asList("new_tag"))
            .features(Arrays.asList("new_feature"))
            .attributes(Collections.singletonMap("size", "large"))
            .rating(4.0)
            .ratingCount(50)
            .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedData.toBuilder()
            .productId(productId).build());

        Product result = productService.updateProduct(updatedData, productId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Product");
        assertThat(result.getPrice()).isEqualTo(120.0);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        String productId = "non-existent";
        Product updatedData = Product.builder().name("New Name").build();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Product result = productService.updateProduct(updatedData, productId);

        assertThat(result).isNull();
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDeleteProduct_Success() {
        String productId = "prod-123";
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testGetProductById_Found() {
        String productId = "prod-123";
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        Product result = productService.getProductById(productId);

        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductById_NotFound() {
        String productId = "non-existent";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Product result = productService.getProductById(productId);

        assertThat(result).isNull();
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetAllProducts_Success() {
        List<Product> products = Arrays.asList(testProduct, testProduct.toBuilder()
            .productId("prod-124")
            .name("Product 2")
            .build());
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).contains(testProduct);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetAllProducts_Empty() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> result = productService.getAllProducts();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetFilteredProducts_WithFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("category", "Electronics");
        queryParams.put("brand", "TestBrand");

        List<Product> products = Arrays.asList(testProduct);
        Page<Product> expectedPage = new PageImpl<>(products, pageable, 1);

        when(productRepository.findByParams(queryParams, pageable)).thenReturn(expectedPage);

        Page<Product> result = productService.getFilteredProducts(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(productRepository, times(1)).findByParams(queryParams, pageable);
    }

    @Test
    void testGetFilteredProducts_ExcludingPaginationParams() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("category", "Electronics");
        queryParams.put("page", "0");
        queryParams.put("size", "10");
        queryParams.put("sort", "name");

        List<Product> products = Arrays.asList(testProduct);
        Page<Product> expectedPage = new PageImpl<>(products, pageable, 1);

        when(productRepository.findByParams(any(Map.class), eq(pageable))).thenReturn(expectedPage);

        Page<Product> result = productService.getFilteredProducts(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(productRepository, times(1)).findByParams(any(Map.class), eq(pageable));
    }

    @Test
    void testGetFilteredProducts_NullQueryParams() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();

        List<Product> products = Arrays.asList(testProduct);
        Page<Product> expectedPage = new PageImpl<>(products, pageable, 1);

        when(productRepository.findByParams(any(Map.class), eq(pageable))).thenReturn(expectedPage);

        Page<Product> result = productService.getFilteredProducts(queryParams, pageable);

        assertThat(result).isNotNull();
        verify(productRepository, times(1)).findByParams(any(Map.class), eq(pageable));
    }

    @Test
    void testGetFilteredProducts_EmptyResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("category", "NonExistent");

        Page<Product> expectedPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(productRepository.findByParams(any(Map.class), eq(pageable))).thenReturn(expectedPage);

        Page<Product> result = productService.getFilteredProducts(queryParams, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isEqualTo(0);
        verify(productRepository, times(1)).findByParams(any(Map.class), eq(pageable));
    }
}
