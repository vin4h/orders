package com.vin4h.orders.order.dto;

import com.vin4h.orders.order.Order;
import com.vin4h.orders.order.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String customerId,
        OrderStatus status,
        BigDecimal total,
        Instant createdAt,
        List<Item> items
) {
    public record Item(String productName, int quantity, BigDecimal unitPrice) {}

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(i -> new Item(i.getProductName(), i.getQuantity(), i.getUnitPrice()))
                        .toList()
        );
    }
}