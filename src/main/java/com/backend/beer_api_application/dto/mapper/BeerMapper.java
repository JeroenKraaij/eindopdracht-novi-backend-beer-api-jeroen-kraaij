package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Category;
import com.backend.beer_api_application.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class BeerMapper {

    private final CategoryRepository categoryRepository;

    public BeerMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public BeerOutputDto transferToBeerOutputDto(Beer beer) {
        BeerOutputDto dto = new BeerOutputDto();
        dto.setId(beer.getId());
        dto.setName(beer.getName());
        dto.setBrand(beer.getBrand());
        dto.setPrice(beer.getPrice());
        return dto;
    }

    public Beer transferToBeerEntity(BeerInputDto beerInputDto) {
        Beer beer = new Beer();
        beer.setName(beerInputDto.getName());
        beer.setBrand(beerInputDto.getBrand());

        Category category = categoryRepository.findById(beerInputDto.getCategory())
                .orElseThrow(() -> new RecordNotFoundException("Category not found with ID " + beerInputDto.getCategory()));
        beer.setCategory(category);

        // map other fields from beerInputDto to beer...
        beer.setDescription(beerInputDto.getDescription());
        beer.setColor(beerInputDto.getColor());
        beer.setBrewery(beerInputDto.getBrewery());
        beer.setCountry(beerInputDto.getCountry());
        beer.setAbv(beerInputDto.getAbv());
        beer.setIbu(beerInputDto.getIbu());
        beer.setFood(beerInputDto.getFood());
        beer.setTemperature(beerInputDto.getTemperature());
        beer.setGlassware(beerInputDto.getGlassware());
        beer.setTaste(beerInputDto.getTaste());
        beer.setPrice(beerInputDto.getPrice());
        beer.setImageUrl(beerInputDto.getImageUrl());

        return beer;
    }
}