package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.dto.mapper.BeerMapper;
import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.ImageUpload;
import com.backend.beer_api_application.repositories.BeerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private final ImageUploadService imageUploadService;

    public BeerService(BeerRepository beerRepository, BeerMapper beerMapper, ImageUploadService imageUploadService) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
        this.imageUploadService = imageUploadService;
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

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return beerRepository.findByName(name).isPresent();
    }

    @Transactional
    public BeerOutputDto addBeer(BeerInputDto beerInputDto) throws IOException {
        Beer beer = beerMapper.transferToBeerEntity(beerInputDto);

        if (beerInputDto.getImageFile() != null && !beerInputDto.getImageFile().isEmpty()) {
            ImageUpload imageUpload = imageUploadService.uploadImage(beer.getId(), beerInputDto.getImageFile());
            beer.addImage(imageUpload);
        }

        beerRepository.save(beer);
        return beerMapper.transferToBeerOutputDto(beer);
    }

    @Transactional
    public BeerOutputDto updateBeer(Long id, BeerInputDto beerInputDto) throws IOException {
        Beer beer = beerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Beer not found"));

        updateBeerEntity(beer, beerInputDto);

        if (beerInputDto.getImageFile() != null && !beerInputDto.getImageFile().isEmpty()) {
            ImageUpload imageUpload = imageUploadService.uploadImage(beer.getId(), beerInputDto.getImageFile());
            beer.addImage(imageUpload);
        }

        beerRepository.save(beer);
        return beerMapper.transferToBeerOutputDto(beer);
    }

    @Transactional
    public void deleteBeer(Long id) {
        beerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Integer getBeerStock(Long beerId) {
        return beerRepository.findById(beerId).map(Beer::getInStock).orElse(null);
    }

    private void updateBeerEntity(Beer beer, BeerInputDto beerInputDto) {
        beer.setName(beerInputDto.getName());
        beer.setBrand(beerInputDto.getBrand());
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
    }
}
