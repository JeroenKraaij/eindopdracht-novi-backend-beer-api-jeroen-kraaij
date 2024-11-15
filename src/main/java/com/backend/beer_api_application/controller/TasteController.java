package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.TasteInputDto;
import com.backend.beer_api_application.dto.mapper.TasteMapper;
import com.backend.beer_api_application.dto.output.TasteOutputDto;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.exceptions.DuplicateResourceException;
import com.backend.beer_api_application.models.Taste;
import com.backend.beer_api_application.services.TasteService;
import jakarta.validation.Valid;
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

    @GetMapping("/tastes")
    public ResponseEntity<List<TasteOutputDto>> getAllTastes() {
        List<TasteOutputDto> tastes = tasteService.getAllTastes();
        return ResponseEntity.ok(tastes);
    }

    @GetMapping("/tastes/{id}")
    public ResponseEntity<?> getTasteById(@PathVariable Long id) {
        Taste taste = tasteService.getTasteById(id)
                .orElseThrow(() -> new RecordNotFoundException("Taste not found with ID: " + id));
        TasteOutputDto tasteDto = TasteMapper.transferToTasteOutputDto(taste);
        return ResponseEntity.ok(tasteDto);
    }

    @PostMapping("/tastes")
    public ResponseEntity<?> addTaste(@Valid @RequestBody TasteInputDto tasteInputDto) {
        TasteOutputDto createdTaste = tasteService.addTaste(tasteInputDto);
        if (createdTaste == null) {
            throw new DuplicateResourceException("Taste with name '" + tasteInputDto.getName() + "' already exists");
        }
        return new ResponseEntity<>(createdTaste, HttpStatus.CREATED);
    }

    @PutMapping("/tastes/{id}")
    public ResponseEntity<?> updateTaste(@PathVariable Long id, @Valid @RequestBody TasteInputDto tasteInputDto) {
        TasteOutputDto updatedTaste = tasteService.updateTaste(id, tasteInputDto);
        if (updatedTaste == null) {
            // Decide between a duplicate or a missing record
            if (tasteService.getTasteById(id).isEmpty()) {
                throw new RecordNotFoundException("Taste not found with ID: " + id);
            } else {
                throw new DuplicateResourceException("Taste with name '" + tasteInputDto.getName() + "' already exists");
            }
        }
        return ResponseEntity.ok(updatedTaste);
    }

    @DeleteMapping("/tastes/{id}")
    public ResponseEntity<?> deleteTaste(@PathVariable Long id) {
        boolean deleted = tasteService.deleteTaste(id);
        if (!deleted) {
            throw new RecordNotFoundException("Taste not found with ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
