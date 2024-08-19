package com.backend.beer_api_application.services;

import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Create or update an order
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Find an order by ID
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Find all orders
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    // Delete an order by ID
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    // Add an OrderLine to an Order
    public Order addOrderLineToOrder(Order order, OrderLine orderLine) {
        order.addOrderLine(orderLine);
        return orderRepository.save(order);
    }
}
