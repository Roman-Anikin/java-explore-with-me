package ru.practicum.compilations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.events.userprivate.PrivateEventService;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.utils.OffsetPageRequest;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationMapper compilationMapper;
    private final PrivateEventService eventService;
    private final CompilationRepository repository;

    @Override
    public List<CompilationDto> getAll(boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations = repository.findAllByPinnedIs(pinned,
                getPageable(from, size, Sort.unsorted()));
        log.info("Получен список подборок {}", compilations);
        return compilationMapper.toDto(compilations);
    }

    @Override
    public CompilationDto getById(Long compilationId) {
        Compilation compilation = checkCompilation(compilationId);
        log.info("Получена подборка {}", compilation);
        return compilationMapper.toDto(compilation);
    }

    @Override
    public CompilationDto add(NewCompilationDto compilationDto) {
        Compilation compilation = repository.save(compilationMapper.fromDto(compilationDto));
        compilation.getEvents().replaceAll(event -> eventService.getEventById(event.getId()));
        log.info("Добавлена подборка событий {}", compilation);
        return compilationMapper.toDto(compilation);
    }

    @Override
    public void delete(Long compilationId) {
        Compilation compilation = checkCompilation(compilationId);
        repository.deleteById(compilationId);
        log.info("Удалена подборка {}", compilation);
    }

    @Override
    @Transactional
    public void deleteCompilationEvent(Long compilationId, Long eventId) {
        Compilation compilation = checkCompilation(compilationId);
        repository.deleteEvent(compilationId, eventId);
        log.info("Из подборки {} удалено событие с id {}", compilation, eventId);
    }

    @Override
    @Transactional
    public void addCompilationEvent(Long compilationId, Long eventId) {
        Compilation compilation = checkCompilation(compilationId);
        repository.addEvent(compilationId, eventId);
        log.info("В подборку {} добавелно событие с id {}", compilation, eventId);
    }

    @Override
    @Transactional
    public void pinningCompilation(boolean pin, Long compilationId) {
        repository.pinningCompilation(pin, compilationId);
        Compilation compilation = checkCompilation(compilationId);
        if (pin) {
            log.info("Подборка закреплена {}", compilation);
        } else {
            log.info("Подборка откреплена {}", compilation);
        }
    }

    private Compilation checkCompilation(Long compilationId) {
        Optional<Compilation> category = repository.findById(compilationId);
        if (category.isEmpty()) {
            throw new ObjectNotFoundException("Подборка с id " + compilationId + " не найдена");
        }
        return category.get();
    }

    private Pageable getPageable(Integer from, Integer size, Sort sort) {
        return new OffsetPageRequest(from, size, sort);
    }
}
