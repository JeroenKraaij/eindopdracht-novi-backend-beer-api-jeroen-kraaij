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

        // Optionally, map image data if required in the output
        if (!beer.getImageUploads().isEmpty()) {
            // Assuming you want to return image details like fileName or URL
            dto.setImageUrl(beer.getImageUploads().get(0).getFileName());
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

        // Map other fields from BeerInputDto to Beer entity
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

        // Image handling is done separately in BeerService
        return beer;
    }
}
