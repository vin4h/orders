package com.vin4h.orders;

import com.vin4h.orders.order.OrderController;
import com.vin4h.orders.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired MockMvc mockMvc;
    @MockitoBean
    OrderService service; // Boot 3.4+; em versões anteriores use @MockBean

    @Test
    void shouldReturn400WhenItemsAreEmpty() throws Exception {
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"customerId":"c1","items":[]}
                    """))
                .andExpect(status().isBadRequest());
    }
}