package com.vin4h.orders.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        @NotBlank String customerId,
        @NotEmpty List<@Valid Item> items
) {
    public record Item(
            @NotBlank String productName,
            @Positive int quantity,
            @NotNull @Positive BigDecimal unitPrice
    ) {}
}