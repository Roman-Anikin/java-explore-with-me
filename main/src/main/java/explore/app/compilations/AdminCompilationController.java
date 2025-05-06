package explore.app.compilations;

import explore.app.compilations.dto.CompilationDto;
import explore.app.compilations.dto.NewCompilationDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
