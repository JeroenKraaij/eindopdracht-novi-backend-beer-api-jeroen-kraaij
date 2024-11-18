package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.CategoryInputDto;
import com.backend.beer_api_application.dto.output.CategoryOutputDto;
import com.backend.beer_api_application.exceptions.DuplicateResourceException;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/categories")
    public ResponseEntity<List<CategoryOutputDto>> getAllCategories() {
        List<CategoryOutputDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping(value = "/categories/{id}")
    public ResponseEntity<CategoryOutputDto> getCategoryById(@PathVariable Long id) {
        CategoryOutputDto category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category not found with ID: " + id));
        return ResponseEntity.ok(category);
    }

    @PostMapping(value = "/categories")
    public ResponseEntity<CategoryOutputDto> createCategory(@Valid @RequestBody CategoryInputDto categoryInputDto) {
        if (categoryService.existsByName(categoryInputDto.getBeerCategoryName())) {
            throw new DuplicateResourceException("Category with name '" + categoryInputDto.getBeerCategoryName() + "' already exists");
        }

        CategoryOutputDto categoryOutputDto = categoryService.addCategory(categoryInputDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryOutputDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryOutputDto);
    }

    @PutMapping(value = "/categories/{id}")
    public ResponseEntity<CategoryOutputDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryInputDto categoryInputDto) {
        if (!categoryService.existsById(id)) {
            throw new RecordNotFoundException("Category not found with ID: " + id);
        }

        if (categoryService.existsByName(categoryInputDto.getBeerCategoryName(), id)) {
            throw new DuplicateResourceException("Category with name '" + categoryInputDto.getBeerCategoryName() + "' already exists");
        }

        CategoryOutputDto updatedCategory = categoryService.updateCategory(id, categoryInputDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (!categoryService.existsById(id)) {
            throw new RecordNotFoundException("Category not found with ID: " + id);
        }

        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
