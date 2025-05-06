package explore.app.events.userprivate;

import explore.app.categories.CategoryMapper;
import explore.app.categories.CategoryService;
import explore.app.events.Event;
import explore.app.events.EventMapper;
import explore.app.events.EventRepository;
import explore.app.events.EventState;
import explore.app.events.dto.FullEventDto;
import explore.app.events.dto.NewEventDto;
import explore.app.events.dto.ShortEventDto;
import explore.app.exceptions.ObjectNotFoundException;
import explore.app.exceptions.ValidationException;
import explore.app.requests.ParticipationRequest;
import explore.app.requests.ParticipationRequestService;
import explore.app.requests.RequestMapper;
import explore.app.requests.RequestStatus;
import explore.app.requests.dto.ParticipationRequestDto;
import explore.app.users.UserMapper;
import explore.app.users.UserService;
import explore.app.utils.CustomDateFormatter;
import explore.app.utils.OffsetPageRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class PrivateEventServiceImpl implements PrivateEventService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ParticipationRequestService requestService;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final RequestMapper requestMapper;
    private final EventRepository repository;
    private final CustomDateFormatter formatter;

    @Override
    public List<ShortEventDto> getAll(Long userId, Integer from, Integer size) {
        List<Event> events = repository.findAllByInitiatorId(userId, getPageable(from, size, Sort.unsorted()));
        log.info("Получен список событий {}", events);
        return eventMapper.toShortDto(events);
    }

    @Override
    public FullEventDto update(Long userId, NewEventDto eventDto) {
        Event event = checkEvent(eventDto.getEventId());
        if (!userId.equals(event.getInitiator().getId())) {
            throw new ValidationException("Редактировать событие может только его инициатор");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Редактировать можно только отмененные события " +
                    "или события в состоянии ожидания модерации");
        }
        if (event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.PENDING);
        }
        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(categoryMapper.fromDto(categoryService.getById(eventDto.getCategory())));
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getEventDate() != null) {
            checkEventDate(eventDto.getEventDate());
            event.setEventDate(formatter.stringToDate(eventDto.getEventDate()));
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != 0) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        repository.save(event);
        log.info("Обновлено событие {}", event);
        return eventMapper.toDto(event);
    }

    @Override
    public FullEventDto add(Long userId, NewEventDto eventDto) {
        checkEventDate(eventDto.getEventDate());
        Event event = eventMapper.fromDto(eventDto);
        event.setCategory(categoryMapper.fromDto(categoryService.getById(eventDto.getCategory())));
        event.setInitiator(userMapper.fromDto(userService.get(new Long[]{userId}, 0, 1).get(0)));
        repository.save(event);
        log.info("Добавлено событие {}", event);
        return eventMapper.toDto(event);
    }

    @Override
    public FullEventDto getById(Long userId, Long eventId) {
        Event event = repository.findByInitiatorIdAndId(userId, eventId);
        if (event == null) {
            throw new ObjectNotFoundException("Событие не найдено");
        }
        log.info("Получено событие {}", event);
        return eventMapper.toDto(event);
    }

    @Override
    public FullEventDto cancel(Long userId, Long eventId) {
        Event event = checkEvent(eventId);
        if (!userId.equals(event.getInitiator().getId())) {
            throw new ValidationException("Отменить событие может только его инициатор");
        }
        if (event.getState().equals(EventState.CANCELED) || event.getState().equals(EventState.PUBLISHED)) {
            throw new ValidationException("Отменить можно только событие в состоянии ожидания модерации");
        }
        event.setState(EventState.CANCELED);
        repository.save(event);
        log.info("Отменено событие {}", event);
        return eventMapper.toDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        Event event = checkEvent(eventId);
        if (!userId.equals(event.getInitiator().getId())) {
            throw new ValidationException("Просматривать заявки на событие может только его инициатор");
        }
        List<ParticipationRequestDto> requests = requestService.getAllByEvent(eventId);
        log.info("Получен список заявок {}", requests);
        return requests;
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long requestId) {
        Event event = checkEvent(eventId);
        ParticipationRequest request = requestService.getById(requestId);
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            return requestMapper.toDto(request);
        }
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new ValidationException("У события достигнут лимит запросов на участие");
        }
        increaseConfirmedRequests(event);
        request.setStatus(RequestStatus.CONFIRMED);
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            requestService.rejectAll(eventId);
        }
        requestService.save(request);
        log.info("Подтверждена заявка {}", request);
        return requestMapper.toDto(request);
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long requestId) {
        checkEvent(eventId);
        ParticipationRequest request = requestService.getById(requestId);
        if (request.getStatus().equals(RequestStatus.REJECTED) || request.getStatus().equals(RequestStatus.CANCELED)) {
            throw new ValidationException("Заявка уже отменена или отклонена");
        }
        request.setStatus(RequestStatus.REJECTED);
        requestService.save(request);
        log.info("Отклонена заявка {}", request);
        return requestMapper.toDto(request);
    }

    @Override
    public void increaseConfirmedRequests(Event event) {
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        repository.save(event);
    }

    @Override
    public void decreaseConfirmedRequests(Event event) {
        if (event.getConfirmedRequests() > 0) {
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            repository.save(event);
        }
    }

    @Override
    public Event getEventById(Long eventId) {
        return checkEvent(eventId);
    }

    private Event checkEvent(Long eventId) {
        Optional<Event> category = repository.findById(eventId);
        if (category.isEmpty()) {
            throw new ObjectNotFoundException("Событие с id " + eventId + " не найдено");
        }
        return category.get();
    }

    private void checkEventDate(String eventDate) {
        String[] lines = eventDate.split(" ");
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(lines[0]), LocalTime.parse(lines[1]));
        if (dateTime.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Дата и время начала события не может быть раньше, " +
                    "чем через два часа от текущего момента");
        }
    }

    private Pageable getPageable(Integer from, Integer size, Sort sort) {
        return new OffsetPageRequest(from, size, sort);
    }
}
