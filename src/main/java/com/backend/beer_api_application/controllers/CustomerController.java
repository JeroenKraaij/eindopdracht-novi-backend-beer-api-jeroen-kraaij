package com.backend.beer_api_application.controllers;

import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.services.CustomerService;
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
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    // Get All Customers by Id
    @GetMapping("{customerId}")
    public Customer getCustomer(
            @PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    // Add a Customer
    @PostMapping
    public void registerCustomer(
            @RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);

    }
    // Update a Customer
    @PutMapping("{customerId}")
    public void deleteCustomer(
            @PathVariable("customerId") Integer customerId,
            @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId, updateRequest);
    }

    // Delete a Customer
    @DeleteMapping("{customerId}")
    public void deleteCustomer(
            @PathVariable("customerId") Integer customerId), {
            customerService.deleteCustomerById(customerId);
    }



}
