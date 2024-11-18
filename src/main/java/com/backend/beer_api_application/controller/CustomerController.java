package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.CustomerInputDto;
import com.backend.beer_api_application.dto.output.CustomerOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.exceptions.UserAlreadyExistsException;
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

    @GetMapping(value = "/customers")
    public ResponseEntity<List<CustomerOutputDto>> getAllCustomers() {
        List<CustomerOutputDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping(value = "/customers/{id}")
    public ResponseEntity<CustomerOutputDto> getCustomerById(@PathVariable("id") Long customerId) {
        if (!customerService.existsById(customerId)) {
            throw new RecordNotFoundException("Customer not found with ID: " + customerId);
        }
        CustomerOutputDto customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @PostMapping(value = "/customers")
    public ResponseEntity<CustomerOutputDto> createCustomer(@Valid @RequestBody CustomerInputDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (customerService.userHasCustomer(authentication.getName())) {
            throw new UserAlreadyExistsException("Customer already exists for user: " + authentication.getName());
        }

        CustomerOutputDto createdCustomer = customerService.addCustomer(request, authentication);
        if (createdCustomer == null) {
            throw new RecordNotFoundException("Customer could not be created.");
        }

        return ResponseEntity.status(201).body(createdCustomer);
    }

    @PutMapping(value = "/customers/{id}")
    public ResponseEntity<CustomerOutputDto> updateCustomer(
            @PathVariable("id") Long customerId, @Valid @RequestBody CustomerInputDto updateRequest) {

        if (!customerService.existsById(customerId)) {
            throw new RecordNotFoundException("Customer not found with ID: " + customerId);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (customerService.userHasCustomer(authentication.getName())) {
            throw new UserAlreadyExistsException("Customer already exists for user: " + authentication.getName());
        }

        CustomerOutputDto updatedCustomer = customerService.updateCustomer(customerId, updateRequest);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long customerId) {
        if (!customerService.existsById(customerId)) {
            throw new RecordNotFoundException("Customer not found with ID: " + customerId);
        }

        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}