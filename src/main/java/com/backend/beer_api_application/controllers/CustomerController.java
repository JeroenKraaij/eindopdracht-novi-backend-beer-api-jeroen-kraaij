package com.backend.beer_api_application.controllers;

import com.backend.beer_api_application.dtos.CustomerRegistrationRequest;
import com.backend.beer_api_application.dtos.CustomerUpdateRequest;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get All Customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Get Customer by Id
    @GetMapping("{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Integer customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }

    // Create a Customer
    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
        return ResponseEntity.status(201).build(); // Return 201 Created status
    }

    // Update a Customer
    @PutMapping("{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") Integer customerId,
                                               @Valid @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId, updateRequest);
        return ResponseEntity.ok().build(); // Return 200 OK status
    }

    // Delete a Customer
    @DeleteMapping("{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build(); // Return 204 No Content status
    }
}
