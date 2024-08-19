package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Category;
import com.backend.beer_api_application.repositories.CategoryRepository;

public class BeerMapper {

    private static CategoryRepository categoryRepository;

    public BeerMapper(CategoryRepository categoryRepository) {
        BeerMapper.categoryRepository = categoryRepository;
    }

    public static BeerOutputDto transferToBeerOutputDto(Beer beer) {
        BeerOutputDto beerOutputDto = new BeerOutputDto();
        beerOutputDto.setId(beer.getId());
        beerOutputDto.setName(beer.getName());
        beerOutputDto.setBrand(beer.getBrand());

        if (beer.getCategory() != null) {
            beerOutputDto.setCategory(beer.getCategory().getBeerCategoryName());
            beerOutputDto.setBeerCategoryType(beer.getCategory().getBeerCategoryType());
        }
        else {
            beerOutputDto.setCategory(null);
            beerOutputDto.setBeerCategoryType(null);
        }

        beerOutputDto.setDescription(beer.getDescription());
        beerOutputDto.setColor(beer.getColor());
        beerOutputDto.setBrewery(beer.getBrewery());
        beerOutputDto.setCountry(beer.getCountry());
        beerOutputDto.setAbv(beer.getAbv() + " %");
        beerOutputDto.setIbu(beer.getIbu());
        beerOutputDto.setFood(beer.getFood());
        beerOutputDto.setTemperature(beer.getTemperature() + " °C");
        beerOutputDto.setGlassware(beer.getGlassware());
        beerOutputDto.setTaste(beer.getTaste());
        beerOutputDto.setPrice(beer.getPrice());
        beerOutputDto.setImageUrl(beer.getImageUrl());
        return beerOutputDto;
    }

    public static Beer transferToBeerEntity(BeerInputDto beerInputDto) {
        Beer beer = new Beer();
        beer.setName(beerInputDto.getName());
        beer.setBrand(beerInputDto.getBrand());

        Category category = categoryRepository.findById(beerInputDto.getCategory())
                .orElseThrow(() -> new RecordNotFoundException("Category not found with ID " + beerInputDto.getCategory()));
        beer.setCategory(category);

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
