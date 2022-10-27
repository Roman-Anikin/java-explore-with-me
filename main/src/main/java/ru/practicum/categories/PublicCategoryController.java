package ru.practicum.categories;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Validated
public class PublicCategoryController {

    private final CategoryService service;

    public PublicCategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryDto> getAll(@PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
                                    @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return service.getAll(from, size);
    }

    @GetMapping(path = "/{categoryId}")
    public CategoryDto getById(@PathVariable Long categoryId) {
        return service.getById(categoryId);
    }
}
