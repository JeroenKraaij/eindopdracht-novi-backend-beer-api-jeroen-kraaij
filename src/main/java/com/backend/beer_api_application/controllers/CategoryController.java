package com.backend.beer_api_application.controllers;

import com.backend.beer_api_application.dtos.CategoryInputDto;
import com.backend.beer_api_application.dtos.CategoryOutputDto;
import com.backend.beer_api_application.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryOutputDto> createCategory(@Valid @RequestBody CategoryInputDto categoryInputDto) {

        CategoryOutputDto categoryOutputDto = categoryService.createCategory(categoryInputDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryOutputDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryOutputDto);
    }
}
