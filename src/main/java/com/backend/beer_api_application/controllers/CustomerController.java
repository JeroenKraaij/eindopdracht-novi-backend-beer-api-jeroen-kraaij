package com.backend.beer_api_application.controllers;

import com.backend.beer_api_application.dtos.CustomerInputDto;
import com.backend.beer_api_application.dtos.CustomerOutputDto;
import com.backend.beer_api_application.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get All Customers
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerOutputDto>> getAllCustomers() {
        List<CustomerOutputDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Get Customer by Id
    @GetMapping("/customers/{Id}")
    public ResponseEntity<CustomerOutputDto> getCustomerById(@PathVariable("Id") Long customerId) {
        CustomerOutputDto customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    // Create a Customer
    @PostMapping("/customers")
    public ResponseEntity<CustomerOutputDto> createCustomer(@Valid @RequestBody CustomerInputDto request) {
        CustomerOutputDto createdCustomer = customerService.createCustomer(request);
        return ResponseEntity.status(201).body(createdCustomer);
    }

    // Update a Customer
    @PutMapping("/customers/{Id}")
    public ResponseEntity<CustomerOutputDto> updateCustomer(@PathVariable("Id") Long customerId,
                                                            @Valid @RequestBody CustomerInputDto updateRequest) {
        CustomerOutputDto updatedCustomer = customerService.updateCustomer(customerId, updateRequest);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Delete a Customer
    @DeleteMapping("/customers/{Id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("Id") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
