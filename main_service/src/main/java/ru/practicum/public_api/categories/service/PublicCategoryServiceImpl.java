package ru.practicum.public_api.categories.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.categories.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size))
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDto getCategory(long catId) {
        return categoryMapper.toDto(categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException("Категории с id = " + catId + " не найдено")));
    }
}
