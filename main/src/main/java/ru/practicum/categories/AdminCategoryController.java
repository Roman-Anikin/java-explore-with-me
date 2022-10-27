package ru.practicum.categories;

import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {

    private final CategoryService service;

    public AdminCategoryController(CategoryService service) {
        this.service = service;
    }

    @PatchMapping
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {
        return service.update(categoryDto);
    }

    @PostMapping
    public CategoryDto add(@Valid @RequestBody CategoryDto categoryDto) {
        return service.add(categoryDto);
    }

    @DeleteMapping(path = "/{categoryId}")
    public void remove(@PathVariable Long categoryId) {
        service.remove(categoryId);
    }
}
