package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Category;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.CategoryRepository;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BeerControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public BeerRepository beerRepository;

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public ObjectMapper objectMapper;

    private Beer testBeerEntity;
    private BeerInputDto testBeerInputDto;

    @BeforeEach
    void setUp() {
        beerRepository.deleteAll();

        // Create and save a test category if it doesn't exist
        if (categoryRepository.findByBeerCategoryName("Lager") == null) {
            Category category = new Category();
            category.setBeerCategoryName("Lager");
            categoryRepository.save(category);
        }

        testBeerEntity = createTestBeerEntity();
        testBeerInputDto = createTestBeerInputDto();
    }

    @Test
    void getAllBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Beer")))
                .andExpect(jsonPath("$[0].brand", is("Test Brand")));
    }

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beers/{id}", testBeerEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Beer")))
                .andExpect(jsonPath("$.brand", is("Test Brand")));
    }

    @Test
    void addBeer() throws Exception {
        mockMvc.perform(post("/api/v1/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeerInputDto)))
               .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Test Beer")))
                .andExpect(jsonPath("$.brand", is("Test Brand")));
    }

    @Test
    void updateBeer() throws Exception {
        BeerInputDto updatedBeerInput = createUpdatedBeerInputDto();

        mockMvc.perform(put("/api/v1/beers/{id}", testBeerEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeerInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Beer")))
                .andExpect(jsonPath("$.brand", is("Updated Brand")));
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beers/{id}", testBeerEntity.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/beers/{id}", testBeerEntity.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBeerStock() throws Exception {
        mockMvc.perform(get("/api/v1/beers/{id}/in-stock", testBeerEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(10)));
    }

    private Beer createTestBeerEntity() {
        Beer beer = new Beer();
        beer.setName("Test Beer");
        beer.setBrand("Test Brand");
        beer.setPrice(BigDecimal.valueOf(5.00));
        beer.setInStock(10);
        beer.setAbv(5.0F);
        beer.setBrewery("Test Brewery");
        beer.setCategory(categoryRepository.findByBeerCategoryName("Lager"));  // Fixed method name
        return beerRepository.save(beer);
    }

    private BeerInputDto createTestBeerInputDto() {
        BeerInputDto dto = new BeerInputDto();
        dto.setName("Test Beer");
        dto.setBrand("Test Brand");
        dto.setPrice(BigDecimal.valueOf(5.00));
        dto.setInStock(10);
        dto.setAbv(5.0F);
        return dto;
    }

    private BeerInputDto createUpdatedBeerInputDto() {
        BeerInputDto dto = new BeerInputDto();
        dto.setName("Updated Beer");
        dto.setBrand("Updated Brand");
        dto.setPrice(BigDecimal.valueOf(6.00));
        dto.setInStock(15);
        return dto;
    }
}
