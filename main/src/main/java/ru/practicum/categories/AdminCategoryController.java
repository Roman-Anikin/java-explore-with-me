package ru.practicum.categories;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.categories.dto.CategoryDto;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {

    private final CategoryService service;

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
