package explore.app.compilations;

import explore.app.compilations.dto.CompilationDto;
import explore.app.compilations.dto.NewCompilationDto;

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
