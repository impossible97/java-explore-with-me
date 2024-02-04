package ru.practicum.public_api.categories.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin_api.categories.dto.CategoryDto;
import ru.practicum.public_api.categories.service.PublicCategoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(params = "/categories")
public class PublicCategoryController {

    private final PublicCategoryService publicCategoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {

        return publicCategoryService.getCategories(from, size);
    }

    @GetMapping("{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        return publicCategoryService.getCategory(catId);
    }
}
