package com.instamart.order_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.instamart.order_service.common.event.OrderEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private static final String TOPIC = "order-topic";

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
