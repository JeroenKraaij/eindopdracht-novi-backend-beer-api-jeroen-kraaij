package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderInputDto;
import com.backend.beer_api_application.dto.mapper.CustomerMapper;
import com.backend.beer_api_application.dto.output.CustomerOutputDto;
import com.backend.beer_api_application.dto.output.OrderOutputDto;
import com.backend.beer_api_application.dto.mapper.OrderMapper;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.backend.beer_api_application.services.CustomerService;
import com.backend.beer_api_application.services.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService, CustomerRepository customerRepository) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    // Get all orders
    @GetMapping(value = "/order")
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        List<OrderOutputDto> orderOutputDtos = orders.stream()
                .map(OrderMapper::transferToOrderOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderOutputDtos);
    }
    // Get an order by ID
    @GetMapping(value = "/order/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findOrderById(id);
        return order.map(value -> ResponseEntity.ok(OrderMapper.transferToOrderOutputDto(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new order
    @PostMapping(value = "/order")
    public CustomerOutputDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Customer not found with ID: " + id));
        return CustomerMapper.transferToCustomerOutputDto(customer);
    }

    // Delete an order by ID
    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderService.findOrderById(id).isPresent()) {
            orderService.deleteOrderById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
