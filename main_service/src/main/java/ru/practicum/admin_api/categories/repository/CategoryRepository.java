package ru.practicum.admin_api.categories.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.admin_api.categories.model.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Category findCategoryById(long categoryId);
}
