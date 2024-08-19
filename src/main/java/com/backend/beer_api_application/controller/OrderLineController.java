package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.mapper.OrderLineMapper;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.models.Beer;
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
    @GetMapping("/order-line")
    public ResponseEntity<List<OrderLineOutputDto>> getAllOrderLines() {
        List<OrderLine> orderLines = orderLineService.findAllOrderLines();
        List<OrderLineOutputDto> orderLineOutputDtos = orderLines.stream()
                .map(OrderLineMapper::transferToOrderLineOutputDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderLineOutputDtos);
    }

    // Get an order line by ID
    @GetMapping("/order-line/{id}")
    public ResponseEntity<OrderLineOutputDto> getOrderLineById(@PathVariable Long id) {
        Optional<OrderLine> orderLine = orderLineService.findOrderLineById(id);
        return orderLine.map(value -> ResponseEntity.ok(OrderLineMapper.transferToOrderLineOutputDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Create a new order line
//    @PostMapping("/order-line")
//    public ResponseEntity<OrderLineOutputDto> createOrderLine(@RequestBody OrderLineOutputDto orderLineInputDto) {
//        Optional<Beer> beer = beerService.getBeerById(orderLineInputDto.getId());
//        if (beer.isPresent()) {
//            OrderLine orderLine = OrderLineMapper.transferToOrderLineEntity(orderLineInputDto, beer.get());
//            OrderLine savedOrderLine = orderLineService.saveOrderLine(orderLine);
//            OrderLineOutputDto orderLineOutputDto = OrderLineMapper.toDto(savedOrderLine);
//            return ResponseEntity.ok(orderLineOutputDto);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    // Delete an order line by ID
    @DeleteMapping("/order-line/{id}")
    public ResponseEntity<Void> deleteOrderLine(@PathVariable Long id) {
        orderLineService.deleteOrderLineById(id);
        return ResponseEntity.noContent().build();
    }
}
