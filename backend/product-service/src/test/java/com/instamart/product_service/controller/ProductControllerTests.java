package com.instamart.product_service.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instamart.product_service.model.Product;
import com.instamart.product_service.service.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testAddProduct_Success() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(testProduct);

        mockMvc.perform(post("/api/products/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testProduct)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value("prod-123"))
            .andExpect(jsonPath("$.name").value("Test Product"))
            .andExpect(jsonPath("$.price").value(100.0));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void testAddProduct_WithNullFields() throws Exception {
        Product product = Product.builder()
            .name("Simple Product")
            .price(50.0)
            .build();

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Simple Product"));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void testGetAllProducts_Success() throws Exception {
        List<Product> products = Arrays.asList(testProduct, testProduct.toBuilder()
            .productId("prod-124")
            .name("Product 2")
            .build());

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products/all")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].productId").value("prod-123"))
            .andExpect(jsonPath("$[1].productId").value("prod-124"))
            .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetAllProducts_Empty() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/products/all")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(0)));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProducts_WithoutFilters() throws Exception {
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> page = new PageImpl<>(products, PageRequest.of(0, 10), 1);

        when(productService.getFilteredProducts(any(Map.class), any())).thenReturn(page);

        mockMvc.perform(get("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .param("page", "0")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].productId").value("prod-123"))
            .andExpect(jsonPath("$.totalElements").value(1));

        verify(productService, times(1)).getFilteredProducts(any(Map.class), any());
    }

    @Test
    void testGetProducts_WithNameFilter() throws Exception {
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> page = new PageImpl<>(products, PageRequest.of(0, 10), 1);

        when(productService.getFilteredProducts(any(Map.class), any())).thenReturn(page);

        mockMvc.perform(get("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .param("name", "Test Product")
            .param("page", "0")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value("Test Product"));

        verify(productService, times(1)).getFilteredProducts(any(Map.class), any());
    }

    @Test
    void testGetProducts_WithCategoryFilter() throws Exception {
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> page = new PageImpl<>(products, PageRequest.of(0, 10), 1);

        when(productService.getFilteredProducts(any(Map.class), any())).thenReturn(page);

        mockMvc.perform(get("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .param("category", "Electronics")
            .param("page", "0")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].category").value("Electronics"));

        verify(productService, times(1)).getFilteredProducts(any(Map.class), any());
    }

    @Test
    void testGetProducts_WithBrandFilter() throws Exception {
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> page = new PageImpl<>(products, PageRequest.of(0, 10), 1);

        when(productService.getFilteredProducts(any(Map.class), any())).thenReturn(page);

        mockMvc.perform(get("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .param("brand", "TestBrand")
            .param("page", "0")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].brand").value("TestBrand"));

        verify(productService, times(1)).getFilteredProducts(any(Map.class), any());
    }

    @Test
    void testGetProducts_WithMultipleFilters() throws Exception {
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> page = new PageImpl<>(products, PageRequest.of(0, 10), 1);

        when(productService.getFilteredProducts(any(Map.class), any())).thenReturn(page);

        mockMvc.perform(get("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .param("category", "Electronics")
            .param("brand", "TestBrand")
            .param("page", "0")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements").value(1));

        verify(productService, times(1)).getFilteredProducts(any(Map.class), any());
    }

    @Test
    void testGetProducts_WithPagination() throws Exception {
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> page = new PageImpl<>(products, PageRequest.of(1, 5), 15);

        when(productService.getFilteredProducts(any(Map.class), any())).thenReturn(page);

        mockMvc.perform(get("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .param("page", "1")
            .param("size", "5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.number").value(1))
            .andExpect(jsonPath("$.size").value(5));

        verify(productService, times(1)).getFilteredProducts(any(Map.class), any());
    }

    @Test
    void testGetProducts_EmptyResult() throws Exception {
        Page<Product> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

        when(productService.getFilteredProducts(any(Map.class), any())).thenReturn(emptyPage);

        mockMvc.perform(get("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .param("category", "NonExistent")
            .param("page", "0")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", org.hamcrest.Matchers.hasSize(0)))
            .andExpect(jsonPath("$.totalElements").value(0));

        verify(productService, times(1)).getFilteredProducts(any(Map.class), any());
    }
}
