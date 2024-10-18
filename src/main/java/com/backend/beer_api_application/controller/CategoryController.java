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
@RequestMapping(value = "api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories
    @GetMapping(value = "/categories")
    public ResponseEntity<List<CategoryOutputDto>> getAllCategories() {
        List<CategoryOutputDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Get all categories by name
    @GetMapping(value = "/categories/{id}")
    public ResponseEntity<CategoryOutputDto> getCategoryById(@PathVariable Long id) {
        CategoryOutputDto categoryOutputDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryOutputDto);
    }

    // Add a new category
    @PostMapping(value = "/categories")
    public ResponseEntity<CategoryOutputDto> createCategory(@Valid @RequestBody CategoryInputDto categoryInputDto) {
        CategoryOutputDto categoryOutputDto = categoryService.addCategory(categoryInputDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryOutputDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryOutputDto);
    }

    // Update an existing category by ID
    @PutMapping(value = "/categories/{id}")
    public ResponseEntity<CategoryOutputDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryInputDto categoryInputDto) {
        CategoryOutputDto updatedCategory = categoryService.updateCategory(id, categoryInputDto);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete a category by ID
    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
