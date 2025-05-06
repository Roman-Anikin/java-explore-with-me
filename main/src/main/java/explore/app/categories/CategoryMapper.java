package explore.app.categories;

import explore.app.categories.dto.CategoryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public Category fromDto(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public List<CategoryDto> toDto(List<Category> categories) {
        return categories
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
