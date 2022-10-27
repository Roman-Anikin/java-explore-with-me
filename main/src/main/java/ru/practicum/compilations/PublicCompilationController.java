package ru.practicum.compilations;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Validated
public class PublicCompilationController {

    private final CompilationService service;

    public PublicCompilationController(CompilationService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(value = "pinned", required = false) boolean pinned,
                                       @PositiveOrZero @RequestParam(value = "from", defaultValue = "0")
                                       Integer from,
                                       @Positive @RequestParam(value = "size", defaultValue = "10")
                                       Integer size) {
        return service.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        return service.getById(compId);
    }
}
