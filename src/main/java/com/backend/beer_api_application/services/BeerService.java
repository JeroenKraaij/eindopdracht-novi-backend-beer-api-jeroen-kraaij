package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dtos.BeerDto;
import com.backend.beer_api_application.dtos.BeerInputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Category;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final CategoryRepository categoryRepository;

    public BeerService(BeerRepository beerRepository, CategoryRepository categoryRepository) {
        this.beerRepository = beerRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BeerDto> getAllBeers() {
        List<Beer> beerList = beerRepository.findAll();
        List<BeerDto> beerDtoList = new ArrayList<>();

        for (Beer beer : beerList) {
            BeerDto dto = transferToDto(beer);
            beerDtoList.add(dto);
        }
        return beerDtoList;
    }

    public List<BeerDto> getAllBeersByBrand(String brand) {
        List<Beer> beerList = beerRepository.findAllBeersByBrandEqualsIgnoreCase(brand);
        List<BeerDto> beerDtoList = new ArrayList<>();

        for (Beer beer : beerList) {
            BeerDto dto = transferToDto(beer);
            beerDtoList.add(dto);
        }
        return beerDtoList;
    }

    public BeerDto getBeerById(Long id) {
        Optional<Beer> beerOptional = beerRepository.findById(id);
        if (beerOptional.isPresent()) {
            Beer beer = beerOptional.get();
            return transferToDto(beer);
        } else {
            throw new RecordNotFoundException("No Beer found with ID " + id);
        }
    }

    public BeerDto addBeer(BeerInputDto beerInputDto) {
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

        beerRepository.save(beer);
        return transferToDto(beer);
    }

    public void deleteBeer(Long id) {
        if (!beerRepository.existsById(id)) {
            throw new RecordNotFoundException("No Beer found with ID " + id);
        }
        beerRepository.deleteById(id);
    }

    public BeerDto updateBeer(Long id, BeerInputDto beerInputDto) {
        Optional<Beer> beerOptional = beerRepository.findById(id);
        if (beerOptional.isPresent()) {
            Beer beer = beerOptional.get();
            beer.setName(beerInputDto.getName());
            beer.setBrand(beerInputDto.getBrand());

            Category category = categoryRepository.findById(beerInputDto.getCategory())
                    .orElseThrow(() -> new RecordNotFoundException("Category not found with ID " + beerInputDto.getCategory()));

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

            beerRepository.save(beer);
            return transferToDto(beer);
        } else {
            throw new RecordNotFoundException("No Beer found with ID " + id);
        }
    }

    private BeerDto transferToDto(Beer beer) {
        BeerDto dto = new BeerDto();
        dto.setId(beer.getId());
        dto.setName(beer.getName());
        dto.setBrand(beer.getBrand());
        dto.setCategory(beer.getCategory().getBeerCategoryName());
        dto.setDescription(beer.getDescription());
        dto.setColor(beer.getColor());
        dto.setBrewery(beer.getBrewery());
        dto.setCountry(beer.getCountry());
        dto.setAbv(beer.getAbv() + " %");
        dto.setIbu(beer.getIbu());
        dto.setFood(beer.getFood());
        dto.setTemperature(beer.getTemperature() + " °C");
        dto.setGlassware(beer.getGlassware());
        dto.setTaste(beer.getTaste());
        dto.setPrice(beer.getPrice());
        dto.setImageUrl(beer.getImageUrl());
        return dto;
    }
}
