package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dtos.CategoryInputDto;
import com.backend.beer_api_application.dtos.CategoryOutputDto;
import com.backend.beer_api_application.models.Category;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final BeerRepository beerRepository;

    public CategoryService(CategoryRepository categoryRepository, BeerRepository beerRepository) {
        this.categoryRepository = categoryRepository;
        this.beerRepository = beerRepository;
    }

    @Transactional
    public CategoryOutputDto createCategory(CategoryInputDto categoryInputDto) {
        if (categoryInputDto == null) {
            logger.error("CategoryInputDto cannot be null");
            throw new IllegalArgumentException("CategoryInputDto cannot be null");
        }

        Category category = new Category();
        category.setBeerCategoryName(categoryInputDto.getBeerCategoryName());
        category.setBeerCategoryType(categoryInputDto.getBeerCategoryType());
        category.setBeerCategoryDescription(categoryInputDto.getBeerCategoryDescription());

        Category savedCategory = categoryRepository.save(category);

        CategoryOutputDto categoryOutputDto = new CategoryOutputDto();
        categoryOutputDto.setId(savedCategory.getId());
        categoryOutputDto.setBeerCategoryName(savedCategory.getBeerCategoryName());
        categoryOutputDto.setBeerCategoryType(savedCategory.getBeerCategoryType());
        categoryOutputDto.setBeerCategoryDescription(savedCategory.getBeerCategoryDescription());

        logger.info("Category created with ID: {}", savedCategory.getId());

        return categoryOutputDto;
    }

    @Transactional(readOnly = true)
    public List<CategoryOutputDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> {
            CategoryOutputDto dto = new CategoryOutputDto();
            dto.setId(category.getId());
            dto.setBeerCategoryName(category.getBeerCategoryName());
            dto.setBeerCategoryType(category.getBeerCategoryType());
            dto.setBeerCategoryDescription(category.getBeerCategoryDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryOutputDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));

        CategoryOutputDto dto = new CategoryOutputDto();
        dto.setId(category.getId());
        dto.setBeerCategoryName(category.getBeerCategoryName());
        dto.setBeerCategoryType(category.getBeerCategoryType());
        dto.setBeerCategoryDescription(category.getBeerCategoryDescription());

        return dto;
    }

    @Transactional
    public CategoryOutputDto updateCategory(Long id, CategoryInputDto categoryInputDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));

        category.setBeerCategoryName(categoryInputDto.getBeerCategoryName());
        category.setBeerCategoryType(categoryInputDto.getBeerCategoryType());
        category.setBeerCategoryDescription(categoryInputDto.getBeerCategoryDescription());

        Category updatedCategory = categoryRepository.save(category);

        CategoryOutputDto categoryOutputDto = new CategoryOutputDto();
        categoryOutputDto.setId(updatedCategory.getId());
        categoryOutputDto.setBeerCategoryName(updatedCategory.getBeerCategoryName());
        categoryOutputDto.setBeerCategoryType(updatedCategory.getBeerCategoryType());
        categoryOutputDto.setBeerCategoryDescription(updatedCategory.getBeerCategoryDescription());

        logger.info("Category updated with ID: {}", updatedCategory.getId());

        return categoryOutputDto;
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));

        categoryRepository.delete(category);
        logger.info("Category deleted with ID: {}", id);
    }
}
