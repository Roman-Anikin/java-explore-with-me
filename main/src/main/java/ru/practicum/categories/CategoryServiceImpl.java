package ru.practicum.categories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.utils.OffsetPageRequest;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository repository) {
        this.categoryMapper = categoryMapper;
        this.repository = repository;
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        List<Category> categories = repository.findAll(getPageable(from, size)).getContent();
        log.info("Получен список категорий {}", categories);
        return categoryMapper.toDto(categories);
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        Category category = checkCategory(categoryId);
        log.info("Получена категория {}", category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category category = checkCategory(categoryDto.getId());
        category.setName(categoryDto.getName());
        log.info("Обновлена категория {}", category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        Category category = repository.save(categoryMapper.fromDto(categoryDto));
        log.info("Добавлена категория {}", category);
        return categoryMapper.toDto(category);
    }

    @Override
    public void remove(Long categoryId) {
        checkCategory(categoryId);
        repository.deleteById(categoryId);
        log.info("Категория с id {} удалена", categoryId);
    }

    private Category checkCategory(Long categoryId) {
        Optional<Category> category = repository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ObjectNotFoundException("Категория с id " + categoryId + " не найдена");
        }
        return category.get();
    }

    private Pageable getPageable(Integer from, Integer size) {
        return new OffsetPageRequest(from, size, Sort.unsorted());
    }
}
