package com.backend.beer_api_application.services;

import com.backend.beer_api_application.enums.OrderStatus;
import com.backend.beer_api_application.exceptions.OrderNotFoundException;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.OrderRepository;
import com.backend.beer_api_application.utils.OrderServiceHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderServiceHelper orderServiceHelper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderServiceHelper orderServiceHelper) {
        this.orderRepository = orderRepository;
        this.orderServiceHelper = orderServiceHelper;
    }

    // Find all orders
    @Transactional
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    // Find an order by ID
    @Transactional
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));
    }

    // Create a new order
    @Transactional
    public Order addNewOrder(Customer customer, List<OrderLine> orderLines) {
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setOrderStatus(OrderStatus.PENDING);

        orderServiceHelper.addOrderLinesToOrder(newOrder, orderLines);

        return orderRepository.save(newOrder);
    }

    // Update the status of an order
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderServiceHelper.getOrderById(orderId);
        order.setOrderStatus(newStatus);
        return orderRepository.save(order);
    }

    // Add OrderLines to an existing order
    @Transactional
    public Order addOrderLineToOrder(Long orderId, OrderLine orderLine) {
        Order order = orderServiceHelper.getOrderById(orderId);
        orderServiceHelper.addOrderLine(order, orderLine);
        return orderRepository.save(order);
    }

    // Remove an OrderLine from an order
    @Transactional
    public Order removeOrderLineFromOrder(Long orderId, Long orderLineId) {
        Order order = orderServiceHelper.getOrderById(orderId);
        orderServiceHelper.removeOrderLine(order, orderLineId);
        return orderRepository.save(order);
    }

    // Delete an order by ID
    @Transactional
    public void deleteOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        orderRepository.deleteById(id);
    }
}
