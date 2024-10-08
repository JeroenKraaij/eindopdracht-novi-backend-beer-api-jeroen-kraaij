package com.backend.beer_api_application.services;

import com.backend.beer_api_application.enums.OrderStatus;
import com.backend.beer_api_application.exceptions.OrderNotFoundException;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.OrderRepository;
import com.backend.beer_api_application.utils.OrderServiceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderServiceHelper orderServiceHelper;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findOrderById_shouldReturnOrder_whenOrderExists() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));

        Order foundOrder = orderService.findOrderById(1L);

        assertNotNull(foundOrder);
        assertEquals(1L, foundOrder.getId());
    }

    @Test
    void findOrderById_shouldThrowException_whenOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(1L));
    }

    @Test
    void addNewOrder_shouldCreateAndSaveOrder() {
        Customer customer = new Customer();
        List<OrderLine> orderLines = new ArrayList<>();
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setCustomer(customer);
        mockOrder.setOrderStatus(OrderStatus.PENDING);

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        Order newOrder = orderService.addNewOrder(customer, orderLines);

        assertNotNull(newOrder);
        assertEquals(OrderStatus.PENDING, newOrder.getOrderStatus());
        assertEquals(customer, newOrder.getCustomer());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderServiceHelper, times(1)).addOrderLinesToOrder(any(Order.class), eq(orderLines));
    }

    @Test
    void updateOrderStatus_shouldUpdateAndSaveOrder() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setOrderStatus(OrderStatus.PENDING);

        when(orderServiceHelper.getOrderById(1L)).thenReturn(existingOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);

        Order updatedOrder = orderService.updateOrderStatus(1L, OrderStatus.COMPLETED);

        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.COMPLETED, updatedOrder.getOrderStatus());
        verify(orderServiceHelper, times(1)).getOrderById(1L);
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    void deleteOrderById_shouldDeleteOrder_whenOrderExists() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        orderService.deleteOrderById(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOrderById_shouldThrowException_whenOrderNotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrderById(1L));
        verify(orderRepository, never()).deleteById(anyLong());
    }

    @Test
    void addOrderLineToOrder_shouldAddOrderLineAndSaveOrder() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        OrderLine newOrderLine = new OrderLine();

        when(orderServiceHelper.getOrderById(1L)).thenReturn(existingOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);

        Order updatedOrder = orderService.addOrderLineToOrder(1L, newOrderLine);

        assertNotNull(updatedOrder);
        verify(orderServiceHelper, times(1)).addOrderLine(existingOrder, newOrderLine);
        verify(orderRepository, times(1)).save(existingOrder);
    }
}