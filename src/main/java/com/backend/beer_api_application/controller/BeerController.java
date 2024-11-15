package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.output.BeerOutputDto;
import com.backend.beer_api_application.dto.input.BeerInputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.services.BeerService;
import com.backend.beer_api_application.dto.mapper.BeerMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @GetMapping(value = "/beers")
    public ResponseEntity<List<BeerOutputDto>> getAllBeers(@RequestParam(value = "brand", required = false) String brand) {
        List<BeerOutputDto> dtos = (brand == null || brand.isEmpty())
                ? beerService.getAllBeers()
                : beerService.getAllBeersByBrand(brand);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/beers/{id}")
    @Transactional
    public ResponseEntity<BeerOutputDto> getBeerById(@PathVariable Long id) {
        Beer beer = beerService.getBeerById(id).orElseThrow(() -> new RecordNotFoundException("Beer with test1 ID " + id + " not found"));
        BeerOutputDto beerDto = beerMapper.transferToBeerOutputDto(beer);
        return ResponseEntity.ok(beerDto);
    }

    @SneakyThrows
    @PostMapping(value = "/beers")
    public ResponseEntity<BeerOutputDto> addBeer(@Valid @RequestBody BeerInputDto beerInputDto) {
        BeerOutputDto dto = beerService.addBeer(beerInputDto);
        URI location = URI.create(String.format("/api/v1/beers/%d", dto.getId()));
        return ResponseEntity.created(location).body(dto);
    }

    @SneakyThrows
    @PutMapping(value = "/beers/{id}")
    public ResponseEntity<BeerOutputDto> updateBeer(@PathVariable Long id, @Valid @RequestBody BeerInputDto newBeer) {
        if (beerService.getBeerById(id).isEmpty()) {
            throw new RecordNotFoundException("Beer with ID " + id + " not found");
        }
        BeerOutputDto dto = beerService.updateBeer(id, newBeer);
        if (dto == null) {
            throw new RecordNotFoundException("Unable to update beer with ID " + id);
        }
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/beers/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        if (beerService.getBeerById(id).isEmpty()) {
            throw new RecordNotFoundException("Beer with ID " + id + " not found");
        }
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/beers/{id}/in-stock")
    public ResponseEntity<Integer> getBeerStock(@PathVariable Long id) {
        Integer stock = beerService.getBeerStock(id);
        if (stock == null) {
            throw new RecordNotFoundException("Beer with ID " + id + " not found");
        }
        return ResponseEntity.ok(stock);
    }
}
