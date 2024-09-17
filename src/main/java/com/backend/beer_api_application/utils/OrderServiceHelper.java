package com.backend.beer_api_application.utils;

import com.backend.beer_api_application.exceptions.OrderNotFoundException;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceHelper {

    private final OrderRepository orderRepository;
    private final OrderCalculator orderCalculator;

    @Autowired
    public OrderServiceHelper(OrderRepository orderRepository, OrderCalculator orderCalculator) {
        this.orderRepository = orderRepository;
        this.orderCalculator = orderCalculator;
    }

    // Get an Order or throw an OrderNotFoundException
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));
    }

    // Add multiple order lines to an order
    public void addOrderLinesToOrder(Order order, List<OrderLine> orderLines) {
        for (OrderLine orderLine : orderLines) {
            addOrderLine(order, orderLine);
        }
        orderCalculator.calculateTotalAmounts(order);  // Recalculate totals after adding lines
    }

    // Add a single order line to an order
    public void addOrderLine(Order order, OrderLine orderLine) {
        order.getOrderLines().add(orderLine);
        orderLine.setOrder(order);
    }

    // Remove a specific order line from an order
    public void removeOrderLine(Order order, Long orderLineId) {
        order.getOrderLines().removeIf(line -> line.getId().equals(orderLineId));
        orderCalculator.calculateTotalAmounts(order);  // Recalculate totals after removing lines
    }
}
