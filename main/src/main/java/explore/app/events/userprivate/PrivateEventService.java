package explore.app.events.userprivate;

import explore.app.events.Event;
import explore.app.events.dto.FullEventDto;
import explore.app.events.dto.NewEventDto;
import explore.app.events.dto.ShortEventDto;
import explore.app.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {

    List<ShortEventDto> getAll(Long userId, Integer from, Integer size);

    FullEventDto update(Long userId, NewEventDto eventDto);

    FullEventDto add(Long userId, NewEventDto eventDto);

    FullEventDto getById(Long userId, Long eventId);

    FullEventDto cancel(Long userId, Long eventId);

    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId);

    void increaseConfirmedRequests(Event event);

    void decreaseConfirmedRequests(Event event);

    Event getEventById(Long eventId);
}
