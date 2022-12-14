package ru.practicum.events.userpublic;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.EventCriteria;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.ShortEventDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/events")
public class PublicEventController {

    private final PublicEventService service;
    private final EventClient client;

    @GetMapping
    public List<ShortEventDto> getAll(EventCriteria criteria, HttpServletRequest request) {
        client.addHit(request);
        return service.getAll(criteria);
    }

    @GetMapping("/{id}")
    public FullEventDto getById(@PathVariable(name = "id") Long eventId, HttpServletRequest request) {
        client.addHit(request);
        return service.getById(eventId);
    }
}
