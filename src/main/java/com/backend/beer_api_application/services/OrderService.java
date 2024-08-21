package com.backend.beer_api_application.services;

import com.backend.beer_api_application.exceptions.OrderNotFoundException;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Find an order by ID
    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Find all orders
    @Transactional(readOnly = true)
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    // Delete an order by ID
    @Transactional
    public void deleteOrderById(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
    }

    // Add an OrderLine to an Order
    @Transactional
    public Optional<Order> addOrderLineToOrder(Long orderId, OrderLine orderLine) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.addOrderLine(orderLine);  // Manage relationship
            orderRepository.save(order);    // Persist changes
            return Optional.of(order);
        } else {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }
    }

    // Remove an OrderLine from an Order
    @Transactional
    public Optional<Order> removeOrderLineFromOrder(Long orderId, Long orderLineId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.getOrderLines().removeIf(ol -> ol.getId().equals(orderLineId));
            orderRepository.save(order);  // Persist changes
            return Optional.of(order);
        } else {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }
    }
}
