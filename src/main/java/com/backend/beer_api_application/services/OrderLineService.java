package com.backend.beer_api_application.services;

import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    @Transactional
    public OrderLine addOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    @Transactional(readOnly = true)
    public OrderLine findOrderLineById(Long id) {
        return orderLineRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<OrderLine> findAllOrderLines() {
        return orderLineRepository.findAll();
    }

    @Transactional
    public OrderLine updateOrderLine(OrderLine updatedOrderLine) {
        return orderLineRepository.save(updatedOrderLine);
    }

    @Transactional
    public void deleteOrderLineById(Long id) {
        orderLineRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean orderLineExists(Long id) {
        return orderLineRepository.existsById(id);
    }
}
