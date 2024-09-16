package com.backend.beer_api_application.services;

import com.backend.beer_api_application.emum.OrderStatus;
import com.backend.beer_api_application.exceptions.OrderNotFoundException;
import com.backend.beer_api_application.models.Customer;
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

    // Create a new order with default PENDING status
    @Transactional
    public Order processNewOrder(Customer customer, List<OrderLine> orderLines) {
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setOrderStatus(OrderStatus.PENDING);  // Default status

        for (OrderLine orderLine : orderLines) {
            newOrder.addOrderLine(orderLine);
        }

        newOrder.calculateTotalAmounts();

        return orderRepository.save(newOrder);
    }

    // Update the status of an order
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus(newStatus);
            return orderRepository.save(order);
        } else {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }
    }

    // Add an OrderLine to an existing order
    @Transactional
    public Optional<Order> addOrderLineToOrder(Long orderId, OrderLine orderLine) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.addOrderLine(orderLine);
            orderRepository.save(order);
            return Optional.of(order);
        } else {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }
    }

    // Remove an OrderLine from an existing order
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
}
