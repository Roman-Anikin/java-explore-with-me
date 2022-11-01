package ru.practicum.requests;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateParticipationRequestController {

    private final ParticipationRequestService service;

    @GetMapping
    public List<ParticipationRequestDto> getAll(@PathVariable Long userId) {
        return service.getAllByUser(userId);
    }

    @PostMapping
    public ParticipationRequestDto add(@PathVariable Long userId, @RequestParam(value = "eventId") Long eventId) {
        return service.add(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable Long userId, @PathVariable Long requestId) {
        return service.cancel(userId, requestId);
    }
}
