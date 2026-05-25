package com.instamart.product_service.common.event;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderEventTests {

    @Test
    void testOrderEventNoArgsConstructor() {
        OrderEvent event = new OrderEvent();

        assertThat(event.getProductId()).isNull();
        assertThat(event.getQuantity()).isNull();
    }

    @Test
    void testOrderEventSettersAndGetters() {
        OrderEvent event = new OrderEvent();

        event.setProductId("prod-123");
        event.setQuantity(5);

        assertThat(event.getProductId()).isEqualTo("prod-123");
        assertThat(event.getQuantity()).isEqualTo(5);
    }

    @Test
    void testOrderEventSetterChaining() {
        OrderEvent event = new OrderEvent();

        event.setProductId("prod-456");
        event.setQuantity(10);

        assertThat(event.getProductId()).isEqualTo("prod-456");
        assertThat(event.getQuantity()).isEqualTo(10);
    }

    @Test
    void testOrderEventWithValues() {
        OrderEvent event = new OrderEvent();
        event.setProductId("prod-789");
        event.setQuantity(3);

        assertThat(event.getProductId()).isEqualTo("prod-789");
        assertThat(event.getQuantity()).isEqualTo(3);
    }

    @Test
    void testOrderEventEqualsAndHashCode() {
        OrderEvent event1 = new OrderEvent();
        event1.setProductId("prod-123");
        event1.setQuantity(5);

        OrderEvent event2 = new OrderEvent();
        event2.setProductId("prod-123");
        event2.setQuantity(5);

        assertThat(event1).isEqualTo(event2);
        assertThat(event1.hashCode()).isEqualTo(event2.hashCode());
    }

    @Test
    void testOrderEventNotEquals() {
        OrderEvent event1 = new OrderEvent();
        event1.setProductId("prod-123");
        event1.setQuantity(5);

        OrderEvent event2 = new OrderEvent();
        event2.setProductId("prod-456");
        event2.setQuantity(10);

        assertThat(event1).isNotEqualTo(event2);
    }

    @Test
    void testOrderEventNotEqualsToNull() {
        OrderEvent event = new OrderEvent();
        event.setProductId("prod-123");
        event.setQuantity(5);

        assertThat(event).isNotEqualTo(null);
    }

    @Test
    void testOrderEventToString() {
        OrderEvent event = new OrderEvent();
        event.setProductId("prod-123");
        event.setQuantity(5);

        String toString = event.toString();

        assertThat(toString).contains("productId");
        assertThat(toString).contains("prod-123");
        assertThat(toString).contains("quantity");
    }

    @Test
    void testOrderEventWithZeroQuantity() {
        OrderEvent event = new OrderEvent();
        event.setProductId("prod-123");
        event.setQuantity(0);

        assertThat(event.getQuantity()).isEqualTo(0);
    }

    @Test
    void testOrderEventWithNegativeQuantity() {
        OrderEvent event = new OrderEvent();
        event.setProductId("prod-123");
        event.setQuantity(-5);

        assertThat(event.getQuantity()).isEqualTo(-5);
    }

    @Test
    void testOrderEventWithLargeQuantity() {
        OrderEvent event = new OrderEvent();
        event.setProductId("prod-123");
        event.setQuantity(Integer.MAX_VALUE);

        assertThat(event.getQuantity()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    void testOrderEventWithEmptyProductId() {
        OrderEvent event = new OrderEvent();
        event.setProductId("");
        event.setQuantity(5);

        assertThat(event.getProductId()).isEmpty();
        assertThat(event.getQuantity()).isEqualTo(5);
    }

    @Test
    void testOrderEventWithSpecialCharactersInProductId() {
        OrderEvent event = new OrderEvent();
        event.setProductId("prod-123_ABC@#$");
        event.setQuantity(5);

        assertThat(event.getProductId()).isEqualTo("prod-123_ABC@#$");
    }

    @Test
    void testOrderEventMultipleSetCalls() {
        OrderEvent event = new OrderEvent();

        event.setProductId("prod-111");
        event.setQuantity(1);

        event.setProductId("prod-222");
        event.setQuantity(2);

        assertThat(event.getProductId()).isEqualTo("prod-222");
        assertThat(event.getQuantity()).isEqualTo(2);
    }

    @Test
    void testOrderEventDataIntegrity() {
        OrderEvent event1 = new OrderEvent();
        event1.setProductId("prod-123");
        event1.setQuantity(5);

        OrderEvent event2 = new OrderEvent();
        event2.setProductId("prod-456");
        event2.setQuantity(10);

        assertThat(event1.getProductId()).isEqualTo("prod-123");
        assertThat(event1.getQuantity()).isEqualTo(5);
        assertThat(event2.getProductId()).isEqualTo("prod-456");
        assertThat(event2.getQuantity()).isEqualTo(10);
    }
}
