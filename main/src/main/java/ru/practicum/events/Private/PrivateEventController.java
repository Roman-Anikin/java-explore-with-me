package ru.practicum.events.Private;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.ShortEventDto;
import ru.practicum.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Validated
public class PrivateEventController {

    private final PrivateEventService service;

    public PrivateEventController(PrivateEventService service) {
        this.service = service;
    }

    @PostMapping
    public FullEventDto add(@PathVariable Long userId, @Valid @RequestBody NewEventDto eventDto) {
        return service.add(userId, eventDto);
    }

    @PatchMapping
    public FullEventDto update(@PathVariable Long userId, @Valid @RequestBody NewEventDto eventDto) {
        return service.update(userId, eventDto);
    }

    @GetMapping
    public List<ShortEventDto> getAllByUser(@PathVariable Long userId,
                                            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0")
                                            Integer from,
                                            @Positive @RequestParam(value = "size", defaultValue = "10")
                                            Integer size) {
        return service.getAll(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public FullEventDto getById(@PathVariable Long userId, @PathVariable Long eventId) {
        return service.getById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public FullEventDto cancelById(@PathVariable Long userId, @PathVariable Long eventId) {
        return service.cancel(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        return service.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable Long userId,
                                                  @PathVariable Long eventId,
                                                  @PathVariable Long reqId) {
        return service.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @PathVariable Long reqId) {
        return service.rejectRequest(userId, eventId, reqId);
    }

}
