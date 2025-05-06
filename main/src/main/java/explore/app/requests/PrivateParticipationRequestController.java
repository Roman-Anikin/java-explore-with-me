package explore.app.requests;

import explore.app.requests.dto.ParticipationRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
