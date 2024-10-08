package com.backend.beer_api_application.integrationTest;

import com.backend.beer_api_application.dto.input.OrderInputDto;
import com.backend.beer_api_application.enums.OrderStatus;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.backend.beer_api_application.repositories.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;

    @BeforeEach
    public void setup() {
        // Maak een klant aan voor gebruik in de tests
        testCustomer = new Customer();
        testCustomer.setFirstname("John");Ooh
        customerRepository.save(testCustomer);
    }

    @Test
    public void testGetAllOrders() throws Exception {
        // Maak een order aan om te testen
        Order order = new Order();
        order.setCustomer(testCustomer);
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        // Test de GET-aanroep voor het ophalen van alle orders
        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer.firstName").value("John"));
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Maak een OrderInputDto aan voor het maken van een nieuwe order
        OrderInputDto orderInputDto = new OrderInputDto();
        orderInputDto.setCustomerId(testCustomer.getId());
        orderInputDto.setOrderLines(Collections.emptyList()); // Lege orderlines in dit voorbeeld

        // Test de POST-aanroep voor het maken van een nieuwe order
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderInputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer.firstName").value("John"))
                .andExpect(jsonPath("$.orderStatus").value("PENDING"));
    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        // Maak een order aan
        Order order = new Order();
        order.setCustomer(testCustomer);
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        // Test de PATCH-aanroep voor het updaten van de orderstatus
        mockMvc.perform(patch("/api/v1/orders/" + order.getId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderStatus.SHIPPED)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value("SHIPPED"));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        // Maak een order aan om te verwijderen
        Order order = new Order();
        order.setCustomer(testCustomer);
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        // Test de DELETE-aanroep voor het verwijderen van de order
        mockMvc.perform(delete("/api/v1/orders/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}