package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderInputDto;
import com.backend.beer_api_application.dto.mapper.OrderMapper;
import com.backend.beer_api_application.dto.output.OrderOutputDto;
import com.backend.beer_api_application.enums.OrderStatus;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class OrderController {

    private final OrderService orderService;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService,
                           CustomerRepository customerRepository, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    // Get all orders
    @GetMapping("/orders")
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        List<OrderOutputDto> orderOutputDtos = orders.stream()
                .map(orderMapper::TransferToOrderOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderOutputDtos);
    }

    // Get an order by ID
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.findOrderById(id);
        return ResponseEntity.ok(orderMapper.TransferToOrderOutputDto(order));
    }

    // Add a new order
    @PostMapping("/orders")
    public ResponseEntity<OrderOutputDto> createOrder(@Valid @RequestBody OrderInputDto orderInputDto) {
        Customer customer = customerRepository.findById(orderInputDto.getCustomerId())
                .orElseThrow(() -> new RecordNotFoundException("Customer not found with ID: " + orderInputDto.getCustomerId()));
        List<OrderLine> orderLines = orderMapper.transferToOrderLineList(orderInputDto.getOrderLines());
        Order newOrder = orderService.addNewOrder(customer, orderLines);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderMapper.TransferToOrderOutputDto(newOrder));
    }

    // Update an existing order by ID
    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderOutputDto> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus newStatus) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
            return ResponseEntity.ok(orderMapper.TransferToOrderOutputDto(updatedOrder));
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete an order by ID
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    // Add an order line to an order
    @PostMapping("/orders/{orderId}/order-lines")
    public ResponseEntity<OrderOutputDto> addOrderLineToOrder(@PathVariable Long orderId, @Valid @RequestBody OrderLine orderLine) {
        try {
            Order updatedOrder = orderService.addOrderLineToOrder(orderId, orderLine);
            return ResponseEntity.ok(orderMapper.TransferToOrderOutputDto(updatedOrder));
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Remove an order line from an order
    @DeleteMapping("/orders/{orderId}/order-lines/{orderLineId}")
    public ResponseEntity<OrderOutputDto> removeOrderLineFromOrder(@PathVariable Long orderId, @PathVariable Long orderLineId) {
        try {
            Order updatedOrder = orderService.removeOrderLineFromOrder(orderId, orderLineId);
            return ResponseEntity.ok(orderMapper.TransferToOrderOutputDto(updatedOrder));
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}