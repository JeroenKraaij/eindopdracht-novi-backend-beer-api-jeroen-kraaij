package com.backend.beer_api_application.controllers;

import com.backend.beer_api_application.dtos.BeerDto;
import com.backend.beer_api_application.dtos.BeerInputDto;
import com.backend.beer_api_application.services.BeerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    // Get all beers
    @GetMapping("/beers")
    public ResponseEntity<List<BeerDto>> getAllBeers(@RequestParam(value = "brand", required = false) Optional<String> brand) {
        List<BeerDto> dtos;
        if (brand.isEmpty()) {
            dtos = beerService.getAllBeers();
        } else {
            dtos = beerService.getAllBeersByBrand(brand.get());
        }
        return ResponseEntity.ok().body(dtos);
    }

    // Get Beer by ID
    @GetMapping("/beers/{id}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("id") Long id) {
        BeerDto beer = beerService.getBeerById(id);
        return ResponseEntity.ok().body(beer);
    }

    // Add a beer
    @PostMapping("/beers")
    public ResponseEntity<BeerDto> addBeer(@RequestBody BeerInputDto beerInputDto) {
        BeerDto dto = beerService.addBeer(beerInputDto);
        return ResponseEntity.created(null).body(dto);
    }

    // Delete a beer by ID
    @DeleteMapping("/beers/{id}")
    public ResponseEntity<Object> deleteBeer(@PathVariable Long id) {
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }

    // Update a beer by ID
    @PutMapping("/beers/{id}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable Long id,  @RequestBody BeerInputDto newBeer) {
        BeerDto dto = beerService.updateBeer(id, newBeer);
        return ResponseEntity.ok().body(dto);
    }
}
