package ru.practicum.events.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.practicum.events.EventCriteria;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.NewEventDto;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/admin/events")
public class AdminEventController {

    private final AdminEventService service;

    @GetMapping
    public List<FullEventDto> getAll(EventCriteria criteria) {
        return service.getAllByAdmin(criteria);
    }

    @PutMapping("/{eventId}")
    public FullEventDto update(@PathVariable Long eventId, @Valid @RequestBody NewEventDto eventDto) {
        return service.updateByAdmin(eventId, eventDto);
    }

    @PatchMapping("/{eventId}/publish")
    public FullEventDto publish(@PathVariable Long eventId) {
        return service.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public FullEventDto reject(@PathVariable Long eventId) {
        return service.rejectEvent(eventId);
    }
}
