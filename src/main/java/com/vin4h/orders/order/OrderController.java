package com.vin4h.orders.order;

import com.vin4h.orders.order.dto.CreateOrderRequest;
import com.vin4h.orders.order.dto.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public OrderResponse find(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping
    public Page<OrderSummary> list(@PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return service.list(pageable);
    }

    @PostMapping("/{id}/pay")
    public OrderResponse pay(@PathVariable UUID id) {
        return service.pay(id);
    }
}