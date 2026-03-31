package com.instamart.product_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.instamart.product_service.common.event.OrderEvent;
import com.instamart.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final ProductRepository repository;

    @KafkaListener(topics = "order-topic", groupId = "product-group")
    public void consume(OrderEvent event) {

        repository.findById(event.getProductId())
                .ifPresent(product -> {

                    product.setStockQuantity(
                            product.getStockQuantity() - event.getQuantity());

                    repository.save(product);

                    System.out.println("Inventory Updated");
                });
    }
}