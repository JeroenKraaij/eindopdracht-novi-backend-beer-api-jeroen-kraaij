package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.mapper.OrderLineMapper;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.exceptions.OutOfStockException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.services.BeerService;
import com.backend.beer_api_application.services.OrderLineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class OrderLineControllerTest {

    @Mock
    private OrderLineService orderLineService;

    @Mock
    private BeerService beerService;

    @Mock
    private OrderLineMapper orderLineMapper;

    @InjectMocks
    private OrderLineController orderLineController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrderLine_shouldReturnCreatedOrderLine_whenStockIsSufficient() {
        // Mock input and entities
        OrderLineInputDto mockOrderLineInputDto = new OrderLineInputDto();
        mockOrderLineInputDto.setBeerId(1L);
        mockOrderLineInputDto.setQuantity(5); // Requesting 5 units of beer with ID 1
        Beer mockBeer = new Beer();
        mockBeer.setId(1L);
        mockBeer.setInStock(10); // 10 units available in stock
        OrderLine mockOrderLine = new OrderLine(mockBeer, 5);
        OrderLineOutputDto mockOrderLineOutputDto = new OrderLineOutputDto();

        // Mock service behavior
        when(beerService.getBeerById(1L)).thenReturn(Optional.of(mockBeer)); // Beer found
        when(orderLineService.addOrderLine(any(OrderLine.class))).thenReturn(mockOrderLine);
        when(orderLineMapper.transferToOrderLineOutputDto(mockOrderLine)).thenReturn(mockOrderLineOutputDto);

        // Act: Call the controller method
        ResponseEntity<?> response = orderLineController.createOrderLine(mockOrderLineInputDto);

        // Assert: Check the response
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockOrderLineOutputDto, response.getBody());
        verify(orderLineService, times(1)).addOrderLine(any(OrderLine.class));
        verify(beerService, times(1)).getBeerById(1L);
    }

    @Test
    void createOrderLine_shouldReturnBadRequest_whenStockIsInsufficient() {
        // Mock input and entities
        OrderLineInputDto mockOrderLineInputDto = new OrderLineInputDto();
        mockOrderLineInputDto.setBeerId(1L);
        mockOrderLineInputDto.setQuantity(15); // Requesting 15 units, stock is 10
        Beer mockBeer = new Beer();
        mockBeer.setId(1L);
        mockBeer.setInStock(16); // 10 units available in stock

        // Mock service behavior
        when(beerService.getBeerById(1L)).thenReturn(Optional.of(mockBeer)); // Beer found
        when(orderLineService.addOrderLine(any(OrderLine.class)))
                .thenThrow(new OutOfStockException("Insufficient stock for beer ID 1"));

        // Act: Call the controller method
        ResponseEntity<?> response = orderLineController.createOrderLine(mockOrderLineInputDto);

        // Assert: Check the response
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient stock for beer ID 1", response.getBody());
        verify(orderLineService, times(1)).addOrderLine(any(OrderLine.class));
        verify(beerService, times(1)).getBeerById(1L);
    }

    @Test
    void updateOrderLine_shouldReturnUpdatedOrderLine_whenStockIsSufficient() {
        // Mock input and entities
        OrderLineInputDto mockOrderLineInputDto = new OrderLineInputDto();
        mockOrderLineInputDto.setBeerId(1L);
        mockOrderLineInputDto.setQuantity(3); // Requesting 3 units of beer with ID 1
        Beer mockBeer = new Beer();
        mockBeer.setId(1L);
        mockBeer.setInStock(10); // 10 units available in stock
        OrderLine mockOrderLine = new OrderLine(mockBeer, 3);
        OrderLineOutputDto mockOrderLineOutputDto = new OrderLineOutputDto();

        // Mock service behavior
        when(beerService.getBeerById(1L)).thenReturn(Optional.of(mockBeer)); // Beer found
        when(orderLineService.updateOrderLine(anyLong(), any(OrderLine.class))).thenReturn(mockOrderLine);
        when(orderLineMapper.transferToOrderLineOutputDto(mockOrderLine)).thenReturn(mockOrderLineOutputDto);

        // Act: Call the controller method
        ResponseEntity<?> response = orderLineController.updateOrderLine(1L, mockOrderLineInputDto);

        // Assert: Check the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrderLineOutputDto, response.getBody());
        verify(orderLineService, times(1)).updateOrderLine(eq(1L), any(OrderLine.class));
        verify(beerService, times(1)).getBeerById(1L);
    }

    @Test
    void createOrderLine_shouldReturnBadRequest_whenBeerNotFound() {
        // Mock input
        OrderLineInputDto mockOrderLineInputDto = new OrderLineInputDto();
        mockOrderLineInputDto.setBeerId(1L);
        mockOrderLineInputDto.setQuantity(5);

        // Mock service behavior
        when(beerService.getBeerById(1L)).thenReturn(Optional.empty()); // Beer not found

        // Act: Call the controller method
        ResponseEntity<?> response = orderLineController.createOrderLine(mockOrderLineInputDto);

        // Assert: Check the response
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Beer with ID 1 not found", response.getBody());
        verify(beerService, times(1)).getBeerById(1L);
    }
}