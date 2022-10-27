package ru.practicum.events.Admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.events.EventCriteria;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.NewEventDto;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
public class AdminEventController {

    private final AdminEventService service;

    public AdminEventController(AdminEventService service) {
        this.service = service;
    }

    @GetMapping
    public List<FullEventDto> getAll(EventCriteria criteria) {
        return service.getAllByAdmin(criteria);
    }

    @PutMapping("/{eventId}")
    public FullEventDto update(@PathVariable Long eventId, @RequestBody NewEventDto eventDto) {
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
