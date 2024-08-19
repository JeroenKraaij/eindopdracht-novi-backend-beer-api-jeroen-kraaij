package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.CategoryInputDto;
import com.backend.beer_api_application.dto.output.CategoryOutputDto;
import com.backend.beer_api_application.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryOutputDto> createCategory(@Valid @RequestBody CategoryInputDto categoryInputDto) {
        CategoryOutputDto categoryOutputDto = categoryService.createCategory(categoryInputDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryOutputDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryOutputDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryOutputDto>> getAllCategories() {
        List<CategoryOutputDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryOutputDto> getCategoryById(@PathVariable Long id) {
        CategoryOutputDto categoryOutputDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryOutputDto);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryOutputDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryInputDto categoryInputDto) {
        CategoryOutputDto updatedCategory = categoryService.updateCategory(id, categoryInputDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
