package explore.app.requests;

import explore.app.events.Event;
import explore.app.events.EventState;
import explore.app.events.userprivate.PrivateEventService;
import explore.app.exceptions.ObjectNotFoundException;
import explore.app.exceptions.ValidationException;
import explore.app.requests.dto.ParticipationRequestDto;
import explore.app.users.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor(onConstructor_ = @Lazy)
@Slf4j
@Service
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final PrivateEventService eventService;
    private final UserService userService;
    private final RequestMapper requestMapper;
    private final ParticipationRequestRepository repository;

    @Override
    public List<ParticipationRequestDto> getAllByUser(Long userId) {
        List<ParticipationRequest> requests = repository.findAllByRequesterId(userId);
        log.info("Получен список заявок {}", requests);
        return requestMapper.toDto(requests);
    }

    @Override
    public ParticipationRequestDto add(Long userId, Long eventId) {
        Event event = eventService.getEventById(eventId);
        if (event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("Инициатор события не может добавить запрос на участие в своём событии");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Нельзя участвовать в неопубликованном событии");
        }
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new ValidationException("У события достигнут лимит запросов на участие");
        }
        ParticipationRequest request = new ParticipationRequest();
        if (event.getRequestModeration()) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
            eventService.increaseConfirmedRequests(event);
        }
        request.setEvent(event);
        request.setRequester(userService.getById(userId));
        request.setCreated(LocalDateTime.now());
        log.info("Добавлена заявка на событие {}", request);
        return requestMapper.toDto(repository.save(request));
    }

    @Override
    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        ParticipationRequest request = checkRequest(requestId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new ValidationException("Отменить можно только собственную заявку");
        }
        if (request.getStatus().equals(RequestStatus.REJECTED) || request.getStatus().equals(RequestStatus.CANCELED)) {
            throw new ValidationException("Заявка уже отменена или отклонена");
        }
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            eventService.decreaseConfirmedRequests(eventService.getEventById(request.getEvent().getId()));
        }
        request.setStatus(RequestStatus.CANCELED);
        log.info("Отменена заявка на событие {}", request);
        return requestMapper.toDto(repository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getAllByEvent(Long eventId) {
        return requestMapper.toDto(repository.findAllByEventId(eventId));
    }

    @Override
    public ParticipationRequest getById(Long requestId) {
        return checkRequest(requestId);
    }

    @Override
    public void save(ParticipationRequest request) {
        repository.save(request);
    }

    @Override
    public void rejectAll(Long eventId) {
        repository.rejectAll(eventId);
    }

    private ParticipationRequest checkRequest(Long requestId) {
        Optional<ParticipationRequest> request = repository.findById(requestId);
        if (request.isEmpty()) {
            throw new ObjectNotFoundException("Заявка с id " + requestId + " не найдена");
        }
        return request.get();
    }
}
