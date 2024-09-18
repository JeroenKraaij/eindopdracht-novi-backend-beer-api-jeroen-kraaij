package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.exceptions.ResourceNotFoundException;
import com.backend.beer_api_application.services.BeerService;
import com.backend.beer_api_application.dto.mapper.BeerMapper;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class BeerController {

    private final BeerService beerService;
    private final BeerMapper beerMapper;

    public BeerController(BeerService beerService, BeerMapper beerMapper) {
        this.beerService = beerService;
        this.beerMapper = beerMapper;
    }

    // Get all beers
    @GetMapping(value = "/beers")
    public ResponseEntity<List<BeerOutputDto>> getAllBeers(@RequestParam(value = "brand", required = false) String brand) {
        List<BeerOutputDto> dtos = (brand == null || brand.isEmpty())
                ? beerService.getAllBeers()
                : beerService.getAllBeersByBrand(brand);
        return ResponseEntity.ok(dtos);
    }

    // Get a beer by ID
    @GetMapping(value = "/beers/{id}")
    public ResponseEntity<BeerOutputDto> getBeerById(@PathVariable Long id) {
        BeerOutputDto beerDto = beerService.getBeerById(id)
                .map(beerMapper::transferToBeerOutputDto)
                .orElseThrow(() -> new ResourceNotFoundException("Beer with ID " + id + " not found"));
        return ResponseEntity.ok(beerDto);
    }

    // Add a new beer
    @SneakyThrows
    @PostMapping(value = "/beers")
    public ResponseEntity<BeerOutputDto> addBeer(@RequestBody BeerInputDto beerInputDto) {
        BeerOutputDto dto = beerService.addBeer(beerInputDto);
        URI location = URI.create(String.format("/api/v1/beers/%d", dto.getId()));
        return ResponseEntity.created(location).body(dto);
    }

    // Update an existing beer by ID
    @SneakyThrows
    @PutMapping(value = "/beers/{id}")
    public ResponseEntity<BeerOutputDto> updateBeer(@PathVariable Long id, @RequestBody BeerInputDto newBeer) {
        BeerOutputDto dto = beerService.updateBeer(id, newBeer);
        return ResponseEntity.ok(dto);
    }

    // Delete a beer by ID
    @DeleteMapping(value = "/beers/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        if (beerService.getBeerById(id).isEmpty()) {
            throw new ResourceNotFoundException("Beer with ID " + id + " not found");
        }
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }

    // Add the inStock for a specific beer by ID
    @GetMapping(value = "/beers/{id}/stock")
    public ResponseEntity<Integer> getBeerStock(@PathVariable Long id) {
        Integer stock = beerService.getBeerStock(id);
        return ResponseEntity.ok(stock);
    }
}
