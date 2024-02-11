package ru.practicum.admin_api.categories.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.categories.service.AdminCategoryService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return adminCategoryService.createCategory(categoryDto);
    }

    @DeleteMapping("{catId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        adminCategoryService.deleteCategory(catId);
    }

    @PatchMapping("{catId}")
    public CategoryDto patchCategory(@PathVariable long catId, @Valid @RequestBody CategoryDto categoryDto) {
        return adminCategoryService.patchCategory(catId, categoryDto);
    }
}
