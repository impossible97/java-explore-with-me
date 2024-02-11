package ru.practicum.admin_api.categories.service;

import ru.practicum.admin_api.categories.dto.CategoryDto;

public interface AdminCategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    void deleteCategory(long catId);

    CategoryDto patchCategory(long catId, CategoryDto categoryDto);
}
