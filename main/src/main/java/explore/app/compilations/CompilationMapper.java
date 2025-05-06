package explore.app.compilations;

import explore.app.compilations.dto.CompilationDto;
import explore.app.compilations.dto.NewCompilationDto;
import explore.app.events.Event;
import explore.app.events.EventMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CompilationMapper {

    private final EventMapper mapper;

    public CompilationDto toDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(),
                mapper.toShortDto(compilation.getEvents()),
                compilation.isPinned(),
                compilation.getTitle());
    }

    public Compilation fromDto(NewCompilationDto compilationDto) {
        return new Compilation(compilationDto.getId(),
                getIds(compilationDto.getEvents()),
                compilationDto.isPinned(),
                compilationDto.getTitle());
    }

    public List<CompilationDto> toDto(List<Compilation> compilations) {
        return compilations
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private List<Event> getIds(List<Long> ids) {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            events.add(new Event());
            events.get(i).setId(ids.get(i));
        }
        return events;
    }
}
