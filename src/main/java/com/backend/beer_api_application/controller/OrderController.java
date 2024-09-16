package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderInputDto;
import com.backend.beer_api_application.dto.mapper.OrderMapper;
import com.backend.beer_api_application.dto.output.OrderOutputDto;
import com.backend.beer_api_application.emum.OrderStatus;
import com.backend.beer_api_application.exceptions.OrderNotFoundException;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.backend.beer_api_application.services.CustomerService;
import com.backend.beer_api_application.services.OrderService;
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
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService, CustomerRepository customerRepository, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    // Get all orders
    @GetMapping(value = "/order")
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        List<OrderOutputDto> orderOutputDtos = orders.stream()
                .map(orderMapper::transferToOrderOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderOutputDtos);
    }

    // Get an order by ID
    @GetMapping(value = "/order/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findOrderById(id);
        return order.map(value -> ResponseEntity.ok(orderMapper.transferToOrderOutputDto(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new order
    @PostMapping(value = "/order")
    public ResponseEntity<OrderOutputDto> createOrder(@Valid @RequestBody OrderInputDto orderInputDto) {
        // Assuming OrderInputDto has customerId and a list of OrderLineInputDto
        Customer customer = customerRepository.findById(orderInputDto.getCustomerId())
                .orElseThrow(() -> new RecordNotFoundException("Customer not found with ID: " + orderInputDto.getCustomerId()));

        // Process new order
        List<OrderLine> orderLines = orderMapper.toOrderLineList(orderInputDto.getOrderLines());
        Order newOrder = orderService.processNewOrder(customer, orderLines);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderMapper.transferToOrderOutputDto(newOrder));
    }

    // Update the status of an order
    @PatchMapping(value = "/order/{id}/status")
    public ResponseEntity<OrderOutputDto> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus newStatus) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
            return ResponseEntity.ok(orderMapper.transferToOrderOutputDto(updatedOrder));
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
