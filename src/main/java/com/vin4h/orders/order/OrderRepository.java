package com.vin4h.orders.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @EntityGraph(attributePaths = "items")
    Optional<Order> findWithItemsById(UUID id);

    @Query(
            value = """
            SELECT new com.vin4h.orders.order.OrderSummary(o.id, o.customerId, o.status, o.total)
            FROM Order o
            """,
            countQuery = "SELECT count(o) FROM Order o"
    )
    Page<OrderSummary> findSummaries(Pageable pageable);
}