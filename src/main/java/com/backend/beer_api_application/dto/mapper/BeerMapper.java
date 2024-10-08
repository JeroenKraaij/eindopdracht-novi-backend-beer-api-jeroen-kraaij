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

    // Convert Beer entity to BeerOutputDto
    public BeerOutputDto transferToBeerOutputDto(Beer beer) {
        BeerOutputDto dto = new BeerOutputDto();
        dto.setId(beer.getId());
        dto.setName(beer.getName());
        dto.setBrand(beer.getBrand());
        dto.setPrice(beer.getPrice());
        dto.setInStock(beer.getInStock());
        if (beer.getCategory() != null) {
            dto.setCategory(beer.getCategory().getBeerCategoryName());
            dto.setBeerCategoryType(beer.getCategory().getBeerCategoryType());
        }
        dto.setDescription(beer.getDescription());
        dto.setColor(beer.getColor());
        dto.setBrewery(beer.getBrewery());
        dto.setCountry(beer.getCountry());
        dto.setAbv(beer.getAbv());
        dto.setIbu(beer.getIbu());
        dto.setFood(beer.getFood());
        dto.setTemperature(beer.getTemperature());
        dto.setGlassware(beer.getGlassware());
        dto.setTaste(beer.getTaste());

        if (!beer.getImageUploads().isEmpty()) {
            dto.setImage(beer.getImageUploads().get(0).getFileName());
        }

        return dto;
    }

    // Convert BeerInputDto to Beer entity
    public Beer transferToBeerEntity(BeerInputDto beerInputDto) {
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
        beer.setInStock(beerInputDto.getInStock());

        return beer;
    }
}
