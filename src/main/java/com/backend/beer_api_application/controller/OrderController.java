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
    public OrderController(OrderService orderService, CustomerRepository customerRepository, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        List<OrderOutputDto> orderOutputDtos = orders.stream()
                .map(orderMapper::TransferToOrderOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderOutputDtos);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.findOrderById(id);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        return ResponseEntity.ok(orderMapper.TransferToOrderOutputDto(order));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderOutputDto> createOrder(@Valid @RequestBody OrderInputDto orderInputDto) {
        Customer customer = customerRepository.findById(orderInputDto.getCustomerId())
                .orElseThrow(() -> new RecordNotFoundException("Customer not found with ID: " + orderInputDto.getCustomerId()));
        List<OrderLine> orderLines = orderMapper.transferToOrderLineList(orderInputDto.getOrderLines());
        Order newOrder = orderService.addNewOrder(customer, orderLines);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.TransferToOrderOutputDto(newOrder));
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderOutputDto> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus newStatus) {
        if (!orderService.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(orderMapper.TransferToOrderOutputDto(updatedOrder));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderService.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/{orderId}/order-lines")
    public ResponseEntity<OrderOutputDto> addOrderLineToOrder(@PathVariable Long orderId, @RequestBody OrderLine orderLine) {
        if (!orderService.existsById(orderId)) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }
        Order updatedOrder = orderService.addOrderLineToOrder(orderId, orderLine);
        return ResponseEntity.ok(orderMapper.TransferToOrderOutputDto(updatedOrder));
    }

    @DeleteMapping("/orders/{orderId}/order-lines/{orderLineId}")
    public ResponseEntity<OrderOutputDto> removeOrderLineFromOrder(@PathVariable Long orderId, @PathVariable Long orderLineId) {
        if (!orderService.existsById(orderId)) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }
        Order updatedOrder = orderService.removeOrderLineFromOrder(orderId, orderLineId);
        return ResponseEntity.ok(orderMapper.TransferToOrderOutputDto(updatedOrder));
    }
}
