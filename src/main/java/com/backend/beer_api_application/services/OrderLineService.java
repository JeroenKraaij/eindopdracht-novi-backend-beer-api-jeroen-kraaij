package com.backend.beer_api_application.services;

import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    // Create or update an OrderLine
    public OrderLine saveOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    // Find an OrderLine by ID
    public Optional<OrderLine> findOrderLineById(Long id) {
        return orderLineRepository.findById(id);
    }

    // Find all OrderLines
    public List<OrderLine> findAllOrderLines() {
        return orderLineRepository.findAll();
    }

    // Delete an OrderLine by ID
    public void deleteOrderLineById(Long id) {
        orderLineRepository.deleteById(id);
    }
}
