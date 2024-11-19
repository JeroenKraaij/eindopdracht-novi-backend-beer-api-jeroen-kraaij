package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.mapper.OrderLineMapper;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.exceptions.OrderLineNotFoundException;
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
import java.util.Optional;
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
    public ResponseEntity<OrderLineOutputDto> getOrderLineById(@PathVariable Long id) {
        Optional<OrderLine> orderLine = Optional.ofNullable(orderLineService.findOrderLineById(id));
        OrderLine orderLineEntity = orderLine.orElseThrow(() -> new OrderLineNotFoundException("OrderLine with ID " + id + " not found"));
        return ResponseEntity.ok(orderLineMapper.transferToOrderLineOutputDto(orderLineEntity));
    }

    @PostMapping(value = "/order-lines")
    public ResponseEntity<OrderLineOutputDto> createOrderLine(@Valid @RequestBody OrderLineInputDto orderLineInputDto) {
        return beerService.getBeerById(orderLineInputDto.getBeerId())
                .map(beer -> {
                    if (beer.getInStock() < orderLineInputDto.getQuantity()) {
                        throw new OutOfStockException("Beer with ID " + orderLineInputDto.getBeerId() + " is out of stock.");
                    }
                    OrderLine orderLine = new OrderLine(beer, orderLineInputDto.getQuantity());
                    OrderLine savedOrderLine = orderLineService.addOrderLine(orderLine);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(orderLineMapper.transferToOrderLineOutputDto(savedOrderLine));
                })
                .orElseThrow(() -> new OrderLineNotFoundException("Beer with ID " + orderLineInputDto.getBeerId() + " not found"));
    }

    @PutMapping(value = "/order-lines/{id}")
    public ResponseEntity<OrderLineOutputDto> updateOrderLine(@PathVariable Long id, @Valid @RequestBody OrderLineInputDto orderLineInputDto) {
        return beerService.getBeerById(orderLineInputDto.getBeerId())
                .map(beer -> {
                    if (beer.getInStock() < orderLineInputDto.getQuantity()) {
                        throw new OutOfStockException("Beer with ID " + orderLineInputDto.getBeerId() + " is out of stock.");
                    }
                    Optional<OrderLine> existingOrderLine = Optional.ofNullable(orderLineService.findOrderLineById(id));
                    OrderLine existingOrderLineEntity = existingOrderLine.orElseThrow(() -> new OrderLineNotFoundException("OrderLine with ID " + id + " not found"));
                    existingOrderLineEntity.setBeer(beer);
                    existingOrderLineEntity.setQuantity(orderLineInputDto.getQuantity());
                    OrderLine updatedOrderLine = orderLineService.updateOrderLine(existingOrderLineEntity);
                    return ResponseEntity.ok(orderLineMapper.transferToOrderLineOutputDto(updatedOrderLine));
                })
                .orElseThrow(() -> new OrderLineNotFoundException("Beer with ID " + orderLineInputDto.getBeerId() + " not found"));
    }

    @DeleteMapping(value = "/order-lines/{id}")
    public ResponseEntity<Void> deleteOrderLine(@PathVariable Long id) {
        if (!orderLineService.orderLineExists(id)) {
            throw new OrderLineNotFoundException("OrderLine with ID " + id + " not found");
        }
        orderLineService.deleteOrderLineById(id);
        return ResponseEntity.noContent().build();
    }
}