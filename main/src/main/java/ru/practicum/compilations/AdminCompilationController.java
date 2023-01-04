package ru.practicum.compilations;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final CompilationService service;

    @PostMapping
    public CompilationDto add(@Valid @RequestBody NewCompilationDto compilationDto) {
        return service.add(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable Long compId) {
        service.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        service.deleteCompilationEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable Long compId, @PathVariable Long eventId) {
        service.addCompilationEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        service.pinningCompilation(false, compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        service.pinningCompilation(true, compId);
    }
}
