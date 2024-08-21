package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.mapper.OrderLineMapper;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.services.BeerService;
import com.backend.beer_api_application.services.OrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class OrderLineController {

    private final OrderLineService orderLineService;
    private final BeerService beerService;

    @Autowired
    public OrderLineController(OrderLineService orderLineService, BeerService beerService) {
        this.orderLineService = orderLineService;
        this.beerService = beerService;
    }

    // Get all order lines
    @GetMapping("/order-lines")
    public ResponseEntity<List<OrderLineOutputDto>> getAllOrderLines() {
        List<OrderLine> orderLines = orderLineService.findAllOrderLines();
        List<OrderLineOutputDto> orderLineOutputDtos = orderLines.stream()
                .map(OrderLineMapper::transferToOrderLineOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderLineOutputDtos);
    }

    // Get an order line by ID
    @GetMapping("/order-lines/{id}")
    public ResponseEntity<OrderLineOutputDto> getOrderLineById(@PathVariable Long id) {
        Optional<OrderLine> orderLine = orderLineService.findOrderLineById(id);
        return orderLine.map(value -> ResponseEntity.ok(OrderLineMapper.transferToOrderLineOutputDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new order line
    @PostMapping("/order-lines")
    public ResponseEntity<OrderLineOutputDto> createOrderLine(@RequestBody OrderLineInputDto orderLineInputDto) {
        return beerService.getBeerById(orderLineInputDto.getBeerId())
                .map(beer -> {
                    OrderLine orderLine = OrderLineMapper.transferToOrderLineEntity(orderLineInputDto, beer);
                    OrderLine savedOrderLine = orderLineService.saveOrderLine(orderLine);
                    OrderLineOutputDto orderLineOutputDto = OrderLineMapper.transferToOrderLineOutputDto(savedOrderLine);
                    return ResponseEntity.ok(orderLineOutputDto);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    // Delete an order line by ID
    @DeleteMapping("/order-lines/{id}")
    public ResponseEntity<Void> deleteOrderLine(@PathVariable Long id) {
        orderLineService.deleteOrderLineById(id);
        return ResponseEntity.noContent().build();
    }
}
