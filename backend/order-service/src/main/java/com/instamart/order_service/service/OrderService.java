package com.instamart.order_service.service;

import org.springframework.stereotype.Service;

import com.instamart.order_service.common.event.OrderEvent;
import com.instamart.order_service.model.Order;
import com.instamart.order_service.producer.OrderProducer;
import com.instamart.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public Order placeOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        OrderEvent event = new OrderEvent();
        event.setProductId(order.getProductId());
        event.setQuantity(order.getQuantity());
        orderProducer.sendOrderEvent(event);
        return savedOrder;
    }
}
