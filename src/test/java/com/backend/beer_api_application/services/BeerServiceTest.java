
package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.mapper.BeerMapper;
import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Loads H2 database and JPA repositories
@Sql(scripts = "/data.sql") // Load data.sql before each test
class BeerServiceTest {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageUploadService imageUploadService;

    private BeerService beerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        beerService = new BeerService(beerRepository, categoryRepository, beerMapper, imageUploadService);
    }

    @Test
    void getAllBeers_shouldReturnListOfBeers_fromDataSql() {
        List<BeerOutputDto> result = beerService.getAllBeers();

        assertNotNull(result);
        assertEquals(15, result.size()); // Assuming 15 beers are inserted via data.sql
    }

    @Test
    void getBeerById_shouldReturnBeer_fromDataSql() {
        Optional<Beer> beer = beerService.getBeerById(1L); // Assuming there's a beer with ID 1

        assertTrue(beer.isPresent());
        assertEquals("Pilsner Urquell", beer.get().getName());
    }

    @Test
    void getBeerById_shouldReturnEmpty_whenBeerDoesNotExist() {
        Optional<Beer> beer = beerService.getBeerById(100L); // Non-existent ID

        assertFalse(beer.isPresent());
    }

    @Test
    void deleteBeer_shouldDeleteBeer_fromDataSql() {
        long beerId = 1L; // Assuming beer with ID 1 exists

        beerService.deleteBeer(beerId);

        Optional<Beer> deletedBeer = beerRepository.findById(beerId);
        assertFalse(deletedBeer.isPresent());
    }

    @Test
    void getBeerStock_shouldReturnStock_fromDataSql() {
        Integer stock = beerService.getBeerStock(1L); // Beer with ID 1

        assertNotNull(stock);
        assertEquals(100, stock); // Assuming beer with ID 1 has 100 stock
    }

    @Test
    void getBeerStock_shouldThrowException_whenBeerDoesNotExist() {
        assertThrows(RecordNotFoundException.class, () -> beerService.getBeerStock(100L)); // Non-existent ID
    }
}
