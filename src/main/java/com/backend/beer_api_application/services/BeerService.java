package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Category;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.backend.beer_api_application.dto.mapper.BeerMapper.transferToBeerEntity;
import static com.backend.beer_api_application.dto.mapper.BeerMapper.transferToDto;

@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final CategoryRepository categoryRepository;

    public BeerService(BeerRepository beerRepository, CategoryRepository categoryRepository) {
        this.beerRepository = beerRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BeerOutputDto> getAllBeers() {
        List<Beer> beerList = beerRepository.findAll();
        List<BeerOutputDto> beerOutputDtoList = new ArrayList<>();

        for (Beer beer : beerList) {
            BeerOutputDto dto = transferToDto(beer);
            beerOutputDtoList.add(dto);
        }
        return beerOutputDtoList;
    }

    public List<BeerOutputDto> getAllBeersByBrand(String brand) {
        List<Beer> beerList = beerRepository.findAllBeersByBrandEqualsIgnoreCase(brand);
        List<BeerOutputDto> beerOutputDtoList = new ArrayList<>();

        for (Beer beer : beerList) {
            BeerOutputDto dto = transferToDto(beer);
            beerOutputDtoList.add(dto);
        }
        return beerOutputDtoList;
    }

    public BeerOutputDto getBeerById(Long id) {
        Optional<Beer> beerOptional = beerRepository.findById(id);
        if (beerOptional.isPresent()) {
            Beer beer = beerOptional.get();
            return transferToDto(beer);
        } else {
            throw new RecordNotFoundException("No Beer found with ID " + id);
        }
    }

    public BeerOutputDto addBeer(BeerInputDto beerInputDto) {
        Beer beer = transferToBeerEntity(beerInputDto);
        beerRepository.save(beer);
        return transferToDto(beer);
    }

    public void deleteBeer(Long id) {
        if (!beerRepository.existsById(id)) {
            throw new RecordNotFoundException("No Beer found with ID " + id);
        }
        beerRepository.deleteById(id);
    }

    public BeerOutputDto updateBeer(Long id, BeerInputDto beerInputDto) {
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


}
