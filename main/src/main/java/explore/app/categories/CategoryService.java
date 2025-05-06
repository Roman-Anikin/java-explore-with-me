package explore.app.categories;

import explore.app.categories.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto getById(Long categoryId);

    CategoryDto update(CategoryDto categoryDto);

    CategoryDto add(CategoryDto categoryDto);

    void remove(Long categoryId);
}
