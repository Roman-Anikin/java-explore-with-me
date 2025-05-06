package explore.app.categories;

import explore.app.categories.dto.CategoryDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
