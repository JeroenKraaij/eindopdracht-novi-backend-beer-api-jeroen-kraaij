package com.backend.beer_api_application.services;

import com.backend.beer_api_application.enums.OrderStatus;
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

    @Transactional
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Transactional
    public Order addNewOrder(Customer customer, List<OrderLine> orderLines) {
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setOrderStatus(OrderStatus.PENDING);
        orderServiceHelper.addOrderLinesToOrder(newOrder, orderLines);
        return orderRepository.save(newOrder);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = findOrderById(orderId);
        if (order != null) {
            order.setOrderStatus(newStatus);
            return orderRepository.save(order);
        }
        return null;
    }

    @Transactional
    public Order addOrderLineToOrder(Long orderId, OrderLine orderLine) {
        Order order = findOrderById(orderId);
        if (order != null) {
            orderServiceHelper.addOrderLine(order, orderLine);
            return orderRepository.save(order);
        }
        return null;
    }

    @Transactional
    public Order removeOrderLineFromOrder(Long orderId, Long orderLineId) {
        Order order = findOrderById(orderId);
        if (order != null) {
            orderServiceHelper.removeOrderLine(order, orderLineId);
            return orderRepository.save(order);
        }
        return null;
    }

    @Transactional
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public boolean existsById(Long orderId) {
        return orderRepository.existsById(orderId);
    }
}
