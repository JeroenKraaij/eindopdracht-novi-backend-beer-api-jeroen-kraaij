package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.mapper.OrderLineMapper;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
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
import static org.mockito.ArgumentMatchers.any;
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
        OrderLineInputDto mockOrderLineInputDto = new OrderLineInputDto();
        mockOrderLineInputDto.setBeerId(1L);
        mockOrderLineInputDto.setQuantity(5);

        Beer mockBeer = new Beer();
        mockBeer.setId(1L);
        mockBeer.setInStock(10);

        OrderLine mockOrderLine = new OrderLine(mockBeer, 5);
        OrderLineOutputDto mockOrderLineOutputDto = new OrderLineOutputDto();

        when(beerService.getBeerById(1L)).thenReturn(Optional.of(mockBeer));
        when(orderLineService.addOrderLine(any(OrderLine.class))).thenReturn(mockOrderLine);
        when(orderLineMapper.transferToOrderLineOutputDto(mockOrderLine)).thenReturn(mockOrderLineOutputDto);

        ResponseEntity<?> response = orderLineController.createOrderLine(mockOrderLineInputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockOrderLineOutputDto, response.getBody());
        verify(orderLineService, times(1)).addOrderLine(any(OrderLine.class));
        verify(beerService, times(1)).getBeerById(1L);
    }

    @Test
    void createOrderLine_shouldReturnBadRequest_whenStockIsInsufficient() {
        OrderLineInputDto mockOrderLineInputDto = new OrderLineInputDto();
        mockOrderLineInputDto.setBeerId(1L);
        mockOrderLineInputDto.setQuantity(15);

        Beer mockBeer = new Beer();
        mockBeer.setId(1L);
        mockBeer.setInStock(10);

        when(beerService.getBeerById(1L)).thenReturn(Optional.of(mockBeer));

        ResponseEntity<?> response = orderLineController.createOrderLine(mockOrderLineInputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Beer with ID 1 is out of stock.", response.getBody());
        verify(orderLineService, never()).addOrderLine(any(OrderLine.class));
        verify(beerService, times(1)).getBeerById(1L);
    }

    @Test
    void updateOrderLine_shouldReturnUpdatedOrderLine_whenStockIsSufficient() {
        OrderLineInputDto mockOrderLineInputDto = new OrderLineInputDto();
        mockOrderLineInputDto.setBeerId(1L);
        mockOrderLineInputDto.setQuantity(5);

        Beer mockBeer = new Beer();
        mockBeer.setId(1L);
        mockBeer.setInStock(8);

        OrderLine mockOrderLine = new OrderLine(mockBeer, 5);
        OrderLineOutputDto mockOrderLineOutputDto = new OrderLineOutputDto();

        when(beerService.getBeerById(1L)).thenReturn(Optional.of(mockBeer));
        when(orderLineService.findOrderLineById(1L)).thenReturn(mockOrderLine);
        when(orderLineService.updateOrderLine(any(OrderLine.class))).thenReturn(mockOrderLine);
        when(orderLineMapper.transferToOrderLineOutputDto(mockOrderLine)).thenReturn(mockOrderLineOutputDto);

        ResponseEntity<?> response = orderLineController.updateOrderLine(1L, mockOrderLineInputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrderLineOutputDto, response.getBody());
        verify(orderLineService, times(1)).updateOrderLine(any(OrderLine.class));
        verify(beerService, times(1)).getBeerById(1L);
    }

    @Test
    void createOrderLine_shouldReturnBadRequest_whenBeerNotFound() {
        OrderLineInputDto mockOrderLineInputDto = new OrderLineInputDto();
        mockOrderLineInputDto.setBeerId(1L);
        mockOrderLineInputDto.setQuantity(5);

        when(beerService.getBeerById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = orderLineController.createOrderLine(mockOrderLineInputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Beer with ID 1 not found", response.getBody());
        verify(beerService, times(1)).getBeerById(1L);
    }
}
