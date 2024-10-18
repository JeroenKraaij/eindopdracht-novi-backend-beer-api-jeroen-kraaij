package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.CustomerInputDto;
import com.backend.beer_api_application.dto.output.CustomerOutputDto;
import com.backend.beer_api_application.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get All Customers
    @GetMapping(value = "/customers")
    public ResponseEntity<List<CustomerOutputDto>> getAllCustomers() {
        List<CustomerOutputDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Get Customer by Id
    @GetMapping(value = "/customers/{id}")
    public ResponseEntity<CustomerOutputDto> getCustomerById(@PathVariable("id") Long customerId) {
        CustomerOutputDto customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    // Add a Customer
    @PostMapping(value = "/customers")
    public ResponseEntity<CustomerOutputDto> createCustomer(@Valid @RequestBody CustomerInputDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerOutputDto createdCustomer = customerService.addCustomer(request, authentication);
        return ResponseEntity.status(201).body(createdCustomer);
    }

    // Update a Customer
    @PutMapping(value = "/customers/{id}")
    public ResponseEntity<CustomerOutputDto> updateCustomer(@PathVariable("id") Long customerId,
                                                            @Valid @RequestBody CustomerInputDto updateRequest) {
        CustomerOutputDto updatedCustomer = customerService.updateCustomer(customerId, updateRequest);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Delete a Customer
    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
