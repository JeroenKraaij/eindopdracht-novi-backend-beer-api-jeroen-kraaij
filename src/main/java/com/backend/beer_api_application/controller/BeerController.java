package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.dto.input.BeerInputDto;
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
    public ResponseEntity<List<BeerOutputDto>> getAllBeers(@RequestParam(value = "brand", required = false) Optional<String> brand) {
        List<BeerOutputDto> dtos;
        if (brand.isEmpty()) {
            dtos = beerService.getAllBeers();
        } else {
            dtos = beerService.getAllBeersByBrand(brand.get());
        }
        return ResponseEntity.ok().body(dtos);
    }

    // Get Beer by ID
    @GetMapping("/beers/{id}")
    public ResponseEntity<BeerOutputDto> getBeerById(@PathVariable("id") Long id) {
        BeerOutputDto beer = beerService.getBeerById(id);
        return ResponseEntity.ok().body(beer);
    }

    // Add a beer
    @PostMapping("/beers")
    public ResponseEntity<BeerOutputDto> addBeer(@RequestBody BeerInputDto beerInputDto) {
        BeerOutputDto dto = beerService.addBeer(beerInputDto);
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
    public ResponseEntity<BeerOutputDto> updateBeer(@PathVariable Long id, @RequestBody BeerInputDto newBeer) {
        BeerOutputDto dto = beerService.updateBeer(id, newBeer);
        return ResponseEntity.ok().body(dto);
    }
}
