package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.mapper.BeerMapper;
import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.exceptions.ResourceNotFoundException;
import com.backend.beer_api_application.services.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class BeerControllerTest {

    @Mock
    private BeerService beerService;

    @Mock
    private BeerMapper beerMapper;

    @InjectMocks
    private BeerController beerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for getBeerStock - Successful case
    @Test
    void getBeerStock_shouldReturnStock_whenBeerExists() {
        // Mock a beer stock value
        int mockStock = 50;
        when(beerService.getBeerStock(anyLong())).thenReturn(mockStock);

        // Act: Call the controller method
        ResponseEntity<Integer> response = beerController.getBeerStock(1L);

        // Assert: Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockStock, response.getBody());
        verify(beerService, times(1)).getBeerStock(1L);
    }

    // Test for getBeerStock - Beer not found
    @Test
    void getBeerStock_shouldThrowResourceNotFound_whenBeerDoesNotExist() {
        // Simulate the service returning a ResourceNotFoundException
        when(beerService.getBeerStock(anyLong())).thenThrow(new ResourceNotFoundException("Beer with ID 1 not found"));

        // Act & Assert: Call the controller method and expect an exception
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> beerController.getBeerStock(1L));

        // Assert: Verify the exception message
        assertEquals("Beer with ID 1 not found", thrown.getMessage());
        verify(beerService, times(1)).getBeerStock(1L);
    }
}
