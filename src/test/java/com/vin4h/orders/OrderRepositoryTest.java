package com.vin4h.orders;

import com.vin4h.orders.order.Order;
import com.vin4h.orders.order.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    OrderRepository repository;
    @Autowired EntityManager entityManager;

    @Test
    void shouldPersistOrderWithItemsAndLoadThemWithEntityGraph() {
        var order = Order.create("c1");
        order.addItem("Keyboard", 2, new BigDecimal("150.00"));
        var saved = repository.saveAndFlush(order);
        entityManager.clear(); // força a leitura vir do banco, não do cache da sessão

        var found = repository.findWithItemsById(saved.getId()).orElseThrow();

        assertThat(found.getItems()).hasSize(1);
        assertThat(found.getTotal()).isEqualByComparingTo("300.00");
    }
}