package ru.practicum.public_api.categories.service;

import ru.practicum.admin_api.categories.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(long catId);
}
