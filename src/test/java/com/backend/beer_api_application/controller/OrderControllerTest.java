package com.backend.beer_api_application.controller;
import com.backend.beer_api_application.dto.mapper.OrderMapper;
import com.backend.beer_api_application.dto.output.OrderOutputDto;

import com.backend.beer_api_application.exceptions.OrderNotFoundException;

import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addOrderLineToOrder_shouldReturnUpdatedOrder_whenSuccessful() {
        // Mock Order and OrderLine
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        OrderLine mockOrderLine = new OrderLine();
        OrderOutputDto mockOrderOutputDto = new OrderOutputDto();

        when(orderService.addOrderLineToOrder(eq(1L), any(OrderLine.class))).thenReturn(mockOrder);
        when(orderMapper.TransferToOrderOutputDto(mockOrder)).thenReturn(mockOrderOutputDto);

        // Act: Call the controller method
        ResponseEntity<OrderOutputDto> response = orderController.addOrderLineToOrder(1L, mockOrderLine);

        // Assert: Verify that the service and mapper were called and that the response is correct
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrderOutputDto, response.getBody());
        verify(orderService, times(1)).addOrderLineToOrder(eq(1L), any(OrderLine.class));
        verify(orderMapper, times(1)).TransferToOrderOutputDto(mockOrder);
    }

    @Test
    void addOrderLineToOrder_shouldReturnNotFound_whenOrderNotFound() {
        OrderLine mockOrderLine = new OrderLine();

        // Simulate the service throwing OrderNotFoundException
        when(orderService.addOrderLineToOrder(eq(1L), any(OrderLine.class))).thenThrow(new OrderNotFoundException("Order not found"));

        // Act: Call the controller method
        ResponseEntity<OrderOutputDto> response = orderController.addOrderLineToOrder(1L, mockOrderLine);

        // Assert: Verify that the response status is NOT_FOUND
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).addOrderLineToOrder(eq(1L), any(OrderLine.class));
    }

    @Test
    void removeOrderLineFromOrder_shouldReturnUpdatedOrder_whenSuccessful() {
        // Mock Order and OrderLine
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        OrderOutputDto mockOrderOutputDto = new OrderOutputDto();

        when(orderService.removeOrderLineFromOrder(eq(1L), eq(1L))).thenReturn(mockOrder);
        when(orderMapper.TransferToOrderOutputDto(mockOrder)).thenReturn(mockOrderOutputDto);

        // Act: Call the controller method
        ResponseEntity<OrderOutputDto> response = orderController.removeOrderLineFromOrder(1L, 1L);

        // Assert: Verify that the service and mapper were called and that the response is correct
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrderOutputDto, response.getBody());
        verify(orderService, times(1)).removeOrderLineFromOrder(eq(1L), eq(1L));
        verify(orderMapper, times(1)).TransferToOrderOutputDto(mockOrder);
    }

    @Test
    void removeOrderLineFromOrder_shouldReturnNotFound_whenOrderNotFound() {
        // Simulate the service throwing OrderNotFoundException
        when(orderService.removeOrderLineFromOrder(eq(1L), eq(1L))).thenThrow(new OrderNotFoundException("Order not found"));

        // Act: Call the controller method
        ResponseEntity<OrderOutputDto> response = orderController.removeOrderLineFromOrder(1L, 1L);

        // Assert: Verify that the response status is NOT_FOUND
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).removeOrderLineFromOrder(eq(1L), eq(1L));
    }

    @Test
    void deleteOrder_shouldDeleteOrder_whenSuccessful() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);

        when(orderService.findOrderById(1L)).thenReturn(mockOrder);

        // Act: Call the controller method
        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        // Assert: Verify the order is deleted
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).deleteOrderById(1L);
    }
}
