package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.dto.mapper.BeerMapper;
import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final CategoryRepository categoryRepository;
    private final BeerMapper beerMapper;

    public BeerService(BeerRepository beerRepository, CategoryRepository categoryRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.categoryRepository = categoryRepository;
        this.beerMapper = beerMapper;
    }

    @Transactional(readOnly = true)
    public List<BeerOutputDto> getAllBeers() {
        return beerRepository.findAll().stream()
                .map(beerMapper::transferToBeerOutputDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BeerOutputDto> getAllBeersByBrand(String brand) {
        return beerRepository.findAllBeersByBrandEqualsIgnoreCase(brand).stream()
                .map(beerMapper::transferToBeerOutputDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Beer> getBeerById(Long id) {
        return beerRepository.findById(id);
    }

    @Transactional
    public BeerOutputDto addBeer(BeerInputDto beerInputDto) {
        Beer beer = beerMapper.transferToBeerEntity(beerInputDto);
        beerRepository.save(beer);
        return beerMapper.transferToBeerOutputDto(beer);
    }

    @Transactional
    public BeerOutputDto updateBeer(Long id, BeerInputDto beerInputDto) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Beer found with ID " + id));

        updateBeerEntity(beer, beerInputDto);
        beerRepository.save(beer);

        return beerMapper.transferToBeerOutputDto(beer);
    }

    @Transactional
    public void deleteBeer(Long id) {
        if (!beerRepository.existsById(id)) {
            throw new RecordNotFoundException("No Beer found with ID " + id);
        }
        beerRepository.deleteById(id);
    }

    private void updateBeerEntity(Beer beer, BeerInputDto beerInputDto) {
    }
}