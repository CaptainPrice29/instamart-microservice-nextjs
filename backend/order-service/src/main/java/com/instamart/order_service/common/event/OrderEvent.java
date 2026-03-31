package com.instamart.order_service.common.event;

import lombok.Data;

@Data
public class OrderEvent {

    private String productId;
    private Integer quantity;
}