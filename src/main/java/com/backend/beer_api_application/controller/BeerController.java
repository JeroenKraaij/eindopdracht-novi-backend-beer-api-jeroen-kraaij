package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.dto.input.BeerInputDto;

import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.services.BeerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    // Get all beers, with an optional brand filter
    @GetMapping("/beers")
    public ResponseEntity<List<BeerOutputDto>> getAllBeers(@RequestParam(value = "brand", required = false) String brand) {
        List<BeerOutputDto> dtos = (brand == null || brand.isEmpty())
                ? beerService.getAllBeers()
                : beerService.getAllBeersByBrand(brand);
        return ResponseEntity.ok(dtos);
    }

    // Get a beer by ID
    @GetMapping("/beers/{id}")
    public ResponseEntity<Optional<Beer>> getBeerById(@PathVariable Long id) {
        Optional<Beer> beer = beerService.getBeerById(id);
        return ResponseEntity.ok(beer);
    }

    // Add a new beer
    @PostMapping("/beers")
    public ResponseEntity<BeerOutputDto> addBeer(@RequestBody BeerInputDto beerInputDto) {
        BeerOutputDto dto = beerService.addBeer(beerInputDto);
        URI location = URI.create(String.format("/api/v1/beers/%d", dto.getId()));  // Assuming BeerOutputDto has getId()
        return ResponseEntity.created(location).body(dto);
    }

    // Update an existing beer by ID
    @PutMapping("/beers/{id}")
    public ResponseEntity<BeerOutputDto> updateBeer(@PathVariable Long id, @RequestBody BeerInputDto newBeer) {
        BeerOutputDto dto = beerService.updateBeer(id, newBeer);
        return ResponseEntity.ok(dto);
    }

    // Delete a beer by ID
    @DeleteMapping("/beers/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }
}
