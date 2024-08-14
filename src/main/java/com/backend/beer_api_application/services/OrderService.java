package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dtos.OrderInputDto;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.backend.beer_api_application.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order placeOrder(OrderInputDto orderInputDto) {
        Customer customer = customerRepository.findById(orderInputDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Beer beer = beerRepository.findById(orderInputDto.getId())
                .orElseThrow(() -> new RuntimeException("Beer not found"));

        Order order = new Order(beer, customer, orderInputDto.getQuantity());

        return orderRepository.save(order);
    }
}
