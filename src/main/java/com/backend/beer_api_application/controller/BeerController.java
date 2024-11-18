package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.exceptions.DuplicateResourceException;
import com.backend.beer_api_application.exceptions.OutOfStockException;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.services.BeerService;
import com.backend.beer_api_application.dto.mapper.BeerMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class BeerController {

    private final BeerService beerService;
    private final BeerMapper beerMapper;

    public BeerController(BeerService beerService, BeerMapper beerMapper) {
        this.beerService = beerService;
        this.beerMapper = beerMapper;
    }

    @GetMapping("/beers")
    public ResponseEntity<List<BeerOutputDto>> getAllBeers(@RequestParam(value = "brand", required = false) String brand) {
        List<BeerOutputDto> dtos = (brand == null || brand.isEmpty())
                ? beerService.getAllBeers()
                : beerService.getAllBeersByBrand(brand);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/beers/{id}")
    @Transactional
    public ResponseEntity<BeerOutputDto> getBeerById(@PathVariable Long id) {
        Beer beer = beerService.getBeerById(id)
                .orElseThrow(() -> new RecordNotFoundException("Beer with ID " + id + " not found"));
        BeerOutputDto beerDto = beerMapper.transferToBeerOutputDto(beer);
        return ResponseEntity.ok(beerDto);
    }

    @PostMapping("/beers")
    public ResponseEntity<BeerOutputDto> addBeer(@Valid @RequestBody BeerInputDto beerInputDto) throws IOException {
        if (beerService.existsByName(beerInputDto.getName())) {
            throw new DuplicateResourceException("Beer with name '" + beerInputDto.getName() + "' already exists");
        }

        BeerOutputDto dto = beerService.addBeer(beerInputDto);
        URI location = URI.create(String.format("/api/v1/beers/%d", dto.getId()));
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/beers/{id}")
    public ResponseEntity<BeerOutputDto> updateBeer(@PathVariable Long id, @Valid @RequestBody BeerInputDto newBeer) throws IOException {
        if (beerService.getBeerById(id).isEmpty()) {
            throw new RecordNotFoundException("Beer with ID " + id + " not found");
        }
        BeerOutputDto dto = beerService.updateBeer(id, newBeer);
        if (dto == null) {
            throw new RecordNotFoundException("Unable to update beer with ID " + id);
        }
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/beers/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        if (beerService.getBeerById(id).isEmpty()) {
            throw new RecordNotFoundException("Beer with ID " + id + " not found");
        }
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/beers/{id}/in-stock")
    public ResponseEntity<Map<String, Object>> getBeerStock(@PathVariable Long id) {
        Integer stock = beerService.getBeerStock(id);
        if (stock == null) {
            throw new RecordNotFoundException("Beer with ID " + id + " not found");
        }
        if (stock == 0) {
            throw new OutOfStockException("Beer with ID " + id + " is out of stock");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Beer is in stock");
        response.put("stock", stock);
        return ResponseEntity.ok(response);
    }
}
