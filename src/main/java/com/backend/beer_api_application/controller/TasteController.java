package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.TasteInputDto;
import com.backend.beer_api_application.dto.output.TasteOutputDto;
import com.backend.beer_api_application.services.TasteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class TasteController {

    private final TasteService tasteService;

    public TasteController(TasteService tasteService) {
        this.tasteService = tasteService;
    }

    // Get all tastes
    @GetMapping("/tastes")
    public ResponseEntity<List<TasteOutputDto>> getAllTastes() {
        List<TasteOutputDto> tastes = tasteService.getAllTastes();
        return ResponseEntity.ok(tastes);
    }

    // Get a single taste by ID
    @GetMapping("/tastes/{id}")
    public ResponseEntity<TasteOutputDto> getTasteById(@PathVariable Long id) {
        TasteOutputDto taste = tasteService.getTasteById(id);
        return ResponseEntity.ok(taste);
    }

    // Add a new taste
    @PostMapping("/tastes")
    public ResponseEntity<TasteOutputDto> addTaste(@RequestBody TasteInputDto tasteInputDto) {
        TasteOutputDto createdTaste = tasteService.addTaste(tasteInputDto);
        return new ResponseEntity<>(createdTaste, HttpStatus.CREATED);
    }

    // Update an existing taste by ID
    @PutMapping("/tastes/{id}")
    public ResponseEntity<TasteOutputDto> updateTaste(@PathVariable Long id, @RequestBody TasteInputDto tasteInputDto) {
        TasteOutputDto updatedTaste = tasteService.updateTaste(id, tasteInputDto);
        return ResponseEntity.ok(updatedTaste);
    }

    // Delete a taste by ID
    @DeleteMapping("/tastes/{id}")
    public ResponseEntity<Void> deleteTaste(@PathVariable Long id) {
        tasteService.deleteTaste(id);
        return ResponseEntity.noContent().build();
    }

}
