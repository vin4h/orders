package com.vin4h.orders.order;

import com.vin4h.orders.order.dto.CreateOrderRequest;
import com.vin4h.orders.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        var order = Order.create(request.customerId());
        request.items().forEach(i -> order.addItem(i.productName(), i.quantity(), i.unitPrice()));
        return OrderResponse.from(repository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(UUID id) {
        return repository.findWithItemsById(id)
                .map(OrderResponse::from)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<OrderSummary> list(Pageable pageable) {
        return repository.findSummaries(pageable);
    }

    @Transactional
    public OrderResponse pay(UUID id) {
        var order = repository.findWithItemsById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.pay();
        return OrderResponse.from(order);
    }
}