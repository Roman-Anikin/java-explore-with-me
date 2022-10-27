package ru.practicum.compilations;

import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

@RestController
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final CompilationService service;

    public AdminCompilationController(CompilationService service) {
        this.service = service;
    }

    @PostMapping
    public CompilationDto add(@RequestBody NewCompilationDto compilationDto) {
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
