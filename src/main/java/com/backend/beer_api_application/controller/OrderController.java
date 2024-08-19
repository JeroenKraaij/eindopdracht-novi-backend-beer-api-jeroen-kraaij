package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderInputDto;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public Order placeOrder(@RequestBody OrderInputDto orderInputDto) {
        return orderService.placeOrder(orderInputDto);
    }
}

