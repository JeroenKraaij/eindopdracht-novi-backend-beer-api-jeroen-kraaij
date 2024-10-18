package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    private Beer testBeerEntity;
    private BeerInputDto testBeerInputDto;

    @BeforeEach
    void setUp() {

        testBeerEntity = createTestBeerEntity();
        testBeerInputDto = createTestBeerInputDto();
    }

    @Test

    void getAllBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].name", is("Test Beer")))
                .andExpect(jsonPath("$[0].brand", is("Test Brand")));
    }

    @Test
    void getBeerById() throws Exception {

        mockMvc.perform(get("/api/v1/beers/1"))
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

        mockMvc.perform(put("/api/v1/beers/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeerInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Beer")))
                .andExpect(jsonPath("$.brand", is("Updated Brand")));
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beers/200"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/beers/200"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBeerStock() throws Exception {
        mockMvc.perform(get("/api/v1/beers/100/in-stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(15)));
    }

    private Beer createTestBeerEntity() {
        Category category = new Category();
        category.setBeerCategoryName("Lager");


        Beer beer = new Beer();
        beer.setName("Test Beer");
        beer.setBrand("Test Brand");
        beer.setPrice(BigDecimal.valueOf(5.00));
        beer.setInStock(10);
        beer.setAbv(5.0F);
        beer.setBrewery("Test Brewery");
        beer.setCategory(category);
//        beer.setCategory(categoryRepository.findByBeerCategoryName("Lager"));  // Fixed method name
//        return beerRepository.save(beer);
        return beer;
    }

    private BeerInputDto createTestBeerInputDto() {
        BeerInputDto dto = new BeerInputDto();
        dto.setName("Test Beer");
        dto.setBrand("Test Brand");
        dto.setPrice(BigDecimal.valueOf(5.00));
        dto.setInStock(10);
        dto.setAbv(5.0F);
        dto.setCategory(1l);
        return dto;
    }

    private BeerInputDto createUpdatedBeerInputDto() {
        BeerInputDto dto = new BeerInputDto();
        dto.setName("Updated Beer");
        dto.setBrand("Updated Brand");
        dto.setPrice(BigDecimal.valueOf(6.00));
        dto.setInStock(15);
        dto.setAbv(6.0F);
        return dto;
    }
}
