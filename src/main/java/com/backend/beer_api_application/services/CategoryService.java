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
        category.setBeerCategoryName(categoryInputDto.getBeerCategoryDescription());

        Category savedCategory = categoryRepository.save(category);

        CategoryOutputDto categoryOutputDto = new CategoryOutputDto();
        categoryOutputDto.setId(savedCategory.getId());
        categoryOutputDto.setBeerCategoryName(savedCategory.getBeerCategoryName());
        categoryOutputDto.setBeerCategoryType(savedCategory.getBeerCategoryType());
        categoryOutputDto.setBeerCategoryDescription(savedCategory.getBeerCategoryDescription());

        logger.info("Category created with ID: {}", savedCategory.getId());

        return categoryOutputDto;
    }
}
