package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderInputDto;
import com.backend.beer_api_application.dto.output.OrderOutputDto;
import com.backend.beer_api_application.dto.mapper.OrderMapper;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.Order;
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
@RequestMapping("api/v1")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    // Get all orders
    @GetMapping("/order")
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        List<OrderOutputDto> orderOutputDtos = orders.stream()
                .map(OrderMapper::transferToOrderOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderOutputDtos);
    }
    // Get an order by ID
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findOrderById(id);
        return order.map(value -> ResponseEntity.ok(OrderMapper.transferToOrderOutputDto(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



    // Create a new order
    @PostMapping("/order")
    public ResponseEntity<OrderOutputDto> createOrder(@Valid @RequestBody OrderInputDto orderInputDto) {
        Optional<Customer> customer = customerService.findCustomerById(orderInputDto.getCustomerId());
        if (customer.isPresent()) {
            Order order = OrderMapper.transferToOrderEntity(orderInputDto, customer.get());
            Order savedOrder = orderService.saveOrder(order);
            OrderOutputDto orderOutputDto = OrderMapper.transferToOrderOutputDto(savedOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderOutputDto);  // Return 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 Not Found
        }
    }

    // Delete an order by ID
    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderService.findOrderById(id).isPresent()) {
            orderService.deleteOrderById(id);
            return ResponseEntity.noContent().build();  // Return 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 Not Found if order does not exist
        }
    }
}
