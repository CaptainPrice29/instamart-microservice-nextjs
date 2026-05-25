package com.instamart.product_service.consumer;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.instamart.product_service.common.event.OrderEvent;
import com.instamart.product_service.model.Product;
import com.instamart.product_service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class OrderConsumerTests {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private OrderConsumer orderConsumer;

    private Product testProduct;
    private OrderEvent orderEvent;

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

        orderEvent = new OrderEvent();
        orderEvent.setProductId("prod-123");
        orderEvent.setQuantity(5);
    }

    @Test
    void testConsume_ProductFound_InventoryUpdated() {
        when(repository.findById("prod-123")).thenReturn(Optional.of(testProduct));
        when(repository.save(any(Product.class))).thenReturn(testProduct.toBuilder()
            .stockQuantity(45)
            .build());

        orderConsumer.consume(orderEvent);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repository, times(1)).findById("prod-123");
        verify(repository, times(1)).save(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();
        assertThat(savedProduct.getStockQuantity()).isEqualTo(45);
    }

    @Test
    void testConsume_ProductFound_StockReduced() {
        int initialStock = 50;
        int quantity = 10;
        int expectedStock = initialStock - quantity;

        testProduct.setStockQuantity(initialStock);
        orderEvent.setQuantity(quantity);

        when(repository.findById("prod-123")).thenReturn(Optional.of(testProduct));
        when(repository.save(any(Product.class))).thenReturn(testProduct.toBuilder()
            .stockQuantity(expectedStock)
            .build());

        orderConsumer.consume(orderEvent);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repository, times(1)).save(productCaptor.capture());

        assertThat(productCaptor.getValue().getStockQuantity()).isEqualTo(expectedStock);
    }

    @Test
    void testConsume_ProductFound_MultipleUpdates() {
        when(repository.findById("prod-123")).thenReturn(Optional.of(testProduct));
        when(repository.save(any(Product.class))).thenReturn(testProduct);

        for (int i = 0; i < 3; i++) {
            orderConsumer.consume(orderEvent);
        }

        verify(repository, times(3)).findById("prod-123");
        verify(repository, times(3)).save(any(Product.class));
    }

    @Test
    void testConsume_ProductNotFound_NoUpdate() {
        when(repository.findById("non-existent")).thenReturn(Optional.empty());

        orderEvent.setProductId("non-existent");
        orderConsumer.consume(orderEvent);

        verify(repository, times(1)).findById("non-existent");
        verify(repository, never()).save(any(Product.class));
    }

    @Test
    void testConsume_WithZeroQuantity() {
        testProduct.setStockQuantity(10);
        orderEvent.setQuantity(0);

        when(repository.findById("prod-123")).thenReturn(Optional.of(testProduct));
        when(repository.save(any(Product.class))).thenReturn(testProduct.toBuilder()
            .stockQuantity(10)
            .build());

        orderConsumer.consume(orderEvent);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repository, times(1)).save(productCaptor.capture());

        assertThat(productCaptor.getValue().getStockQuantity()).isEqualTo(10);
    }

    @Test
    void testConsume_WithLargeQuantity() {
        testProduct.setStockQuantity(100);
        orderEvent.setQuantity(50);

        when(repository.findById("prod-123")).thenReturn(Optional.of(testProduct));
        when(repository.save(any(Product.class))).thenReturn(testProduct.toBuilder()
            .stockQuantity(50)
            .build());

        orderConsumer.consume(orderEvent);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repository, times(1)).save(productCaptor.capture());

        assertThat(productCaptor.getValue().getStockQuantity()).isEqualTo(50);
    }

    @Test
    void testConsume_StockBecomesZero() {
        testProduct.setStockQuantity(5);
        orderEvent.setQuantity(5);

        when(repository.findById("prod-123")).thenReturn(Optional.of(testProduct));
        when(repository.save(any(Product.class))).thenReturn(testProduct.toBuilder()
            .stockQuantity(0)
            .build());

        orderConsumer.consume(orderEvent);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repository, times(1)).save(productCaptor.capture());

        assertThat(productCaptor.getValue().getStockQuantity()).isEqualTo(0);
    }

    @Test
    void testConsume_NullEvent() {
        assertThatThrownBy(() -> orderConsumer.consume(null)).isInstanceOf(NullPointerException.class);
        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }

    @Test
    void testConsume_NullProductId() {
        orderEvent.setProductId(null);

        assertThatThrownBy(() -> orderConsumer.consume(orderEvent)).isInstanceOf(NullPointerException.class);
        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }
}
