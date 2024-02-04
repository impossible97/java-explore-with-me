package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.categories.model.Category;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();

        category.setName(categoryDto.getName());
        return category;
    }

    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(categoryDto.getName());
        return categoryDto;
    }
}
