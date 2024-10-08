package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.mapper.OrderLineMapper;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.exceptions.OutOfStockException;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.services.BeerService;
import com.backend.beer_api_application.services.OrderLineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1")
public class OrderLineController {

    private final OrderLineService orderLineService;
    private final BeerService beerService;
    private final OrderLineMapper orderLineMapper;

    @Autowired
    public OrderLineController(OrderLineService orderLineService, BeerService beerService,
                               OrderLineMapper orderLineMapper) {
        this.orderLineService = orderLineService;
        this.beerService = beerService;
        this.orderLineMapper = orderLineMapper;
    }

    // Get all order lines
    @GetMapping(value = "/order-lines")
    public ResponseEntity<List<OrderLineOutputDto>> getAllOrderLines() {
        List<OrderLine> orderLines = orderLineService.findAllOrderLines();
        List<OrderLineOutputDto> orderLineOutputDtos = orderLines.stream()
                .map(orderLineMapper::transferToOrderLineOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderLineOutputDtos);
    }

    // Get an order line by ID
    @GetMapping(value = "/order-lines/{id}")
    public ResponseEntity<OrderLineOutputDto> getOrderLineById(@PathVariable Long id) {
        OrderLine orderLine = orderLineService.findOrderLineById(id);
        return ResponseEntity.ok(orderLineMapper.transferToOrderLineOutputDto(orderLine));
    }

    // Create a new order line with stock validation
    @PostMapping(value = "/order-lines")
    public ResponseEntity<?> createOrderLine(@Valid @RequestBody OrderLineInputDto orderLineInputDto) {
        return beerService.getBeerById(orderLineInputDto.getBeerId())
                .map(beer -> {
                    try {
                        // Create order line after validating stock
                        OrderLine orderLine = new OrderLine(beer, orderLineInputDto.getQuantity());
                        OrderLine savedOrderLine = orderLineService.addOrderLine(orderLine);
                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(orderLineMapper.transferToOrderLineOutputDto(savedOrderLine));
                    } catch (IllegalArgumentException | OutOfStockException ex) {
                        // Return a 400 Bad Request if stock is insufficient
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Beer with ID " + orderLineInputDto.getBeerId() + " not found"));
    }

    // Update an order line with stock validation
    @PutMapping(value = "/order-lines/{id}")
    public ResponseEntity<?> updateOrderLine(
            @PathVariable Long id,
            @Valid @RequestBody OrderLineInputDto orderLineInputDto) {

        return beerService.getBeerById(orderLineInputDto.getBeerId())
                .map(beer -> {
                    try {
                        // Update order line after validating stock
                        OrderLine updatedOrderLine = new OrderLine(beer, orderLineInputDto.getQuantity());
                        OrderLine savedOrderLine = orderLineService.updateOrderLine(id, updatedOrderLine);
                        return ResponseEntity.ok(orderLineMapper.transferToOrderLineOutputDto(savedOrderLine));
                    } catch (IllegalArgumentException | OutOfStockException ex) {
                        // Return a 400 Bad Request if stock is insufficient
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Beer with ID " + orderLineInputDto.getBeerId() + " not found"));
    }

    // Delete an order line by ID
    @DeleteMapping(value = "/order-lines/{id}")
    public ResponseEntity<Void> deleteOrderLine(@PathVariable Long id) {
        orderLineService.deleteOrderLineById(id);
        return ResponseEntity.noContent().build();
    }
}
