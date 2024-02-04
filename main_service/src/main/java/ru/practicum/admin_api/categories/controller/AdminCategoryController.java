package ru.practicum.admin_api.categories.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.admin_api.categories.service.AdminCategoryService;

@RestController
@AllArgsConstructor
@RequestMapping(params = "/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return adminCategoryService.createCategory(categoryDto);
    }

    @DeleteMapping("{catId}")
    public void deleteCategory(@PathVariable long catId) {
        adminCategoryService.deleteCategory(catId);
    }

    @PatchMapping("{catId}")
    public CategoryDto patchCategory(@PathVariable long catId, @RequestBody CategoryDto categoryDto) {
        return adminCategoryService.patchCategory(catId, categoryDto);
    }
}
