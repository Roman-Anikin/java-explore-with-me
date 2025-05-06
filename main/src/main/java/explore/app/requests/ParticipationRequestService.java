package explore.app.requests;

import explore.app.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {

    ParticipationRequestDto add(Long userId, Long eventId);

    ParticipationRequestDto cancel(Long userId, Long requestId);

    List<ParticipationRequestDto> getAllByUser(Long userId);

    List<ParticipationRequestDto> getAllByEvent(Long eventId);

    ParticipationRequest getById(Long requestId);

    void save(ParticipationRequest request);

    void rejectAll(Long eventId);
}
