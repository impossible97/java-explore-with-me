package ru.practicum.admin_api.categories.service;

import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.categories.dto.NewCategoryDto;

public interface AdminCategoryService {
    CategoryDto createCategory(NewCategoryDto categoryDto);

    void deleteCategory(long catId);

    CategoryDto patchCategory(long catId, NewCategoryDto categoryDto);
}
