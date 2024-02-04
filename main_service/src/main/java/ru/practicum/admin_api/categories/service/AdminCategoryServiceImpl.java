package ru.practicum.admin_api.categories.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.categories.model.Category;
import ru.practicum.admin_api.categories.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;

@Service
@AllArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }

    @Override
    public void deleteCategory(long catId) {
        categoryRepository.delete(categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException("Категория с таким catId = " + catId + " не найдена")));
    }

    @Override
    public CategoryDto patchCategory(long catId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException("Категория с таким catId = " + catId + " не найдена"));
        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }
        return categoryMapper.toDto(categoryRepository.save(category));
    }
}
