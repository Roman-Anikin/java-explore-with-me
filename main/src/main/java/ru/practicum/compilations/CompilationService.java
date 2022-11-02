package ru.practicum.compilations;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getAll(boolean pinned, Integer from, Integer size);

    CompilationDto getById(Long compilationId);

    CompilationDto add(NewCompilationDto compilationDto);

    void delete(Long compilationId);

    void deleteCompilationEvent(Long compilationId, Long eventId);

    void addCompilationEvent(Long compilationId, Long eventId);

    void pinningCompilation(boolean pin, Long compilationId);
}
