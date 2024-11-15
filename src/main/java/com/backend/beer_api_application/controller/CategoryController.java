package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.CategoryInputDto;
import com.backend.beer_api_application.dto.output.CategoryOutputDto;
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
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(category -> {
                    CategoryOutputDto categoryOutputDto = new CategoryOutputDto();
                    categoryOutputDto.setId(category.getId());
                    categoryOutputDto.setBeerCategoryName(category.getBeerCategoryName());
                    categoryOutputDto.setBeerCategoryType(category.getBeerCategoryType());
                    categoryOutputDto.setBeerCategoryDescription(category.getBeerCategoryDescription());
                    return ResponseEntity.ok(categoryOutputDto);
                })
                .orElseThrow(() -> new RecordNotFoundException("Category not found with ID: " + id));
    }

    @PostMapping(value = "/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryInputDto categoryInputDto) {
        CategoryOutputDto categoryOutputDto = categoryService.addCategory(categoryInputDto);
        if (categoryOutputDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Category input");
        }

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryOutputDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryOutputDto);
    }

    @PutMapping(value = "/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryInputDto categoryInputDto) {
        CategoryOutputDto updatedCategory = categoryService.updateCategory(id, categoryInputDto);
        if (updatedCategory == null) {
            throw new RecordNotFoundException("Category not found with ID: " + id);
        }
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (!deleted) {
            throw new RecordNotFoundException("Category not found with ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
