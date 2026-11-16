package com.vin4h.orders.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderSummary(UUID id, String customerId, OrderStatus status,
                           BigDecimal total) {
}