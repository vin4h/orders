package com.vin4h.orders;

import com.vin4h.orders.order.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void shouldNotPayTwice() {
        var order = Order.create("c1");
        order.pay();

        assertThatThrownBy(order::pay)
                .isInstanceOf(IllegalStateException.class);
    }
}