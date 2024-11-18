package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.CategoryInputDto;
import com.backend.beer_api_application.dto.output.CategoryOutputDto;
import com.backend.beer_api_application.models.Category;
import com.backend.beer_api_application.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryOutputDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CategoryOutputDto> getCategoryById(Long id) {
        return categoryRepository.findById(id).map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.findByBeerCategoryName(name).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name, Long excludeId) {
        return categoryRepository.findByBeerCategoryName(name)
                .map(category -> !category.getId().equals(excludeId))
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Transactional
    public CategoryOutputDto addCategory(CategoryInputDto categoryInputDto) {
        Category category = new Category();
        category.setBeerCategoryName(categoryInputDto.getBeerCategoryName());
        category.setBeerCategoryType(categoryInputDto.getBeerCategoryType());
        category.setBeerCategoryDescription(categoryInputDto.getBeerCategoryDescription());

        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    @Transactional
    public CategoryOutputDto updateCategory(Long id, CategoryInputDto categoryInputDto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setBeerCategoryName(categoryInputDto.getBeerCategoryName());
        category.setBeerCategoryType(categoryInputDto.getBeerCategoryType());
        category.setBeerCategoryDescription(categoryInputDto.getBeerCategoryDescription());

        Category updatedCategory = categoryRepository.save(category);
        return mapToDto(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryOutputDto mapToDto(Category category) {
        CategoryOutputDto dto = new CategoryOutputDto();
        dto.setId(category.getId());
        dto.setBeerCategoryName(category.getBeerCategoryName());
        dto.setBeerCategoryType(category.getBeerCategoryType());
        dto.setBeerCategoryDescription(category.getBeerCategoryDescription());
        return dto;
    }
}