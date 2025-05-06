package explore.app.categories;

import explore.app.categories.dto.CategoryDto;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/categories")
@Validated
public class PublicCategoryController {

    private final CategoryService service;

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
