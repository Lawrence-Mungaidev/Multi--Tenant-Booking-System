package com.merlin.Multi_Tenant_Booking_System20.Category;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
        category.setCreatedAt(LocalDateTime.now());

        return category;
    }

    public CategoryResponseDto toCategoryResponseDto(Category category) {
        return  new CategoryResponseDto(category.getId(), category.getName(), category.getDescription());
    }
}
