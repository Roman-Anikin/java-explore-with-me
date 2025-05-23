package explore.app.categories;

import explore.app.categories.dto.CategoryDto;
import explore.app.exceptions.ObjectAlreadyExistException;
import explore.app.exceptions.ObjectNotFoundException;
import explore.app.utils.OffsetPageRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository repository;

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
        checkName(categoryDto.getName());
        category.setName(categoryDto.getName());
        log.info("Обновлена категория {}", category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        checkName(categoryDto.getName());
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

    private void checkName(String name) {
        repository.findByName(name).ifPresent(
                category -> {
                    throw new ObjectAlreadyExistException("Категория с названием " + name + " уже существует.");
                });
    }
}
