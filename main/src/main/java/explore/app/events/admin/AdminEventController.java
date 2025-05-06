package explore.app.events.admin;

import explore.app.events.EventCriteria;
import explore.app.events.dto.FullEventDto;
import explore.app.events.dto.NewEventDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
