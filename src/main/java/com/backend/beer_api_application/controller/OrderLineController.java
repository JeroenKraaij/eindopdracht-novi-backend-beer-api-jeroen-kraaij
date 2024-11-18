package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.mapper.OrderLineMapper;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
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

    @GetMapping(value = "/order-lines")
    public ResponseEntity<List<OrderLineOutputDto>> getAllOrderLines() {
        List<OrderLine> orderLines = orderLineService.findAllOrderLines();
        List<OrderLineOutputDto> orderLineOutputDtos = orderLines.stream()
                .map(orderLineMapper::transferToOrderLineOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderLineOutputDtos);
    }

    @GetMapping(value = "/order-lines/{id}")
    public ResponseEntity<?> getOrderLineById(@PathVariable Long id) {
        OrderLine orderLine = orderLineService.findOrderLineById(id);
        if (orderLine == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OrderLine with ID " + id + " not found");
        }
        return ResponseEntity.ok(orderLineMapper.transferToOrderLineOutputDto(orderLine));
    }

    @PostMapping(value = "/order-lines")
    public ResponseEntity<?> createOrderLine(@Valid @RequestBody OrderLineInputDto orderLineInputDto) {
        return beerService.getBeerById(orderLineInputDto.getBeerId())
                .map(beer -> {
                    if (beer.getInStock() < orderLineInputDto.getQuantity()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Beer with ID " + orderLineInputDto.getBeerId() + " is out of stock.");
                    }
                    OrderLine orderLine = new OrderLine(beer, orderLineInputDto.getQuantity());
                    OrderLine savedOrderLine = orderLineService.addOrderLine(orderLine);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(orderLineMapper.transferToOrderLineOutputDto(savedOrderLine));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Beer with ID " + orderLineInputDto.getBeerId() + " not found"));
    }

    @PutMapping(value = "/order-lines/{id}")
    public ResponseEntity<?> updateOrderLine(@PathVariable Long id, @Valid @RequestBody OrderLineInputDto orderLineInputDto) {
        return beerService.getBeerById(orderLineInputDto.getBeerId())
                .map(beer -> {
                    if (beer.getInStock() < orderLineInputDto.getQuantity()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Beer with ID " + orderLineInputDto.getBeerId() + " is out of stock.");
                    }
                    OrderLine existingOrderLine = orderLineService.findOrderLineById(id);
                    if (existingOrderLine == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("OrderLine with ID " + id + " not found");
                    }
                    existingOrderLine.setBeer(beer);
                    existingOrderLine.setQuantity(orderLineInputDto.getQuantity());
                    OrderLine updatedOrderLine = orderLineService.updateOrderLine(existingOrderLine);
                    return ResponseEntity.ok(orderLineMapper.transferToOrderLineOutputDto(updatedOrderLine));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Beer with ID " + orderLineInputDto.getBeerId() + " not found"));
    }

    @DeleteMapping(value = "/order-lines/{id}")
    public ResponseEntity<?> deleteOrderLine(@PathVariable Long id) {
        if (!orderLineService.orderLineExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OrderLine with ID " + id + " not found");
        }
        orderLineService.deleteOrderLineById(id);
        return ResponseEntity.noContent().build();
    }
}
