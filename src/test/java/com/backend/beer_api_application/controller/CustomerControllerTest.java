package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.CustomerInputDto;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        // Clean up the repository to ensure each test starts with a clean slate
        customerRepository.deleteAll();

        // Create a test customer
        testCustomer = new Customer();
        testCustomer.setSurname("John Doe");
        testCustomer.setPhone("1234567890");

        // Save the test customer to the database
        customerRepository.save(testCustomer);
    }

    @Test
    void getAllCustomers() throws Exception {
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].phone", is("1234567890")));
    }

    @Test
    void getCustomerById() throws Exception {
        mockMvc.perform(get("/api/v1/customers/{id}", testCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.phone", is("1234567890")));
    }

    @Test
    void createCustomer() throws Exception {
        CustomerInputDto newCustomer = new CustomerInputDto();
        newCustomer.setFirstname("Jane");
        newCustomer.setPhone("0987654321");

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Jane")))
                .andExpect(jsonPath("$.phone", is("0987654321")));
    }

    @Test
    void updateCustomer() throws Exception {
        CustomerInputDto updatedCustomer = new CustomerInputDto();
        updatedCustomer.setSurname("Smith");
        updatedCustomer.setPhone("9876543210");

        mockMvc.perform(put("/api/v1/customers/{id}", testCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Smith")))
                .andExpect(jsonPath("$.phone", is("9876543210")));
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/{id}", testCustomer.getId()))
                .andExpect(status().isNoContent());

        // Verify that the customer has been deleted
        mockMvc.perform(get("/api/v1/customers/{id}", testCustomer.getId()))
                .andExpect(status().isNotFound());
    }
}
