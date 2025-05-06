package explore.app.events.admin;

import explore.app.categories.CategoryMapper;
import explore.app.categories.CategoryService;
import explore.app.events.*;
import explore.app.events.dto.FullEventDto;
import explore.app.events.dto.NewEventDto;
import explore.app.exceptions.ObjectNotFoundException;
import explore.app.exceptions.ValidationException;
import explore.app.utils.CustomDateFormatter;
import explore.app.utils.OffsetPageRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class AdminEventServiceImpl implements AdminEventService {

    private final CategoryService categoryService;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final EventRepository repository;
    private final CustomDateFormatter formatter;

    @Override
    public List<FullEventDto> getAllByAdmin(EventCriteria criteria) {
        Specification<Event> specification = EventSpecifications.getAdminSpecifications(criteria);
        List<Event> events = repository.findAll(specification,
                        getPageable(criteria.getFrom(), criteria.getSize(), Sort.unsorted()))
                .getContent();
        log.info("Получен список событий {}", events);
        return eventMapper.toDto(events);
    }

    @Override
    public FullEventDto updateByAdmin(Long eventId, NewEventDto eventDto) {
        Event event = checkEvent(eventId);
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
        if (eventDto.getLocation() != null) {
            event.setLocation(eventDto.getLocation());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        repository.save(event);
        log.info("Обновлено событие {}", event);
        return eventMapper.toDto(event);
    }

    @Override
    public FullEventDto publishEvent(Long eventId) {
        Event event = checkEvent(eventId);
        if (event.getEventDate().plusHours(1).isAfter(LocalDateTime.now()) &&
                event.getState().equals(EventState.PENDING)) {
            event.setPublishedOn(LocalDateTime.now());
            event.setState(EventState.PUBLISHED);
            repository.save(event);
            log.info("Опубликовано событие {}", event);
        }
        return eventMapper.toDto(event);
    }

    @Override
    public FullEventDto rejectEvent(Long eventId) {
        Event event = checkEvent(eventId);
        if (event.getState().equals(EventState.PENDING)) {
            event.setState(EventState.CANCELED);
            repository.save(event);
            log.info("Отклонено событие {}", event);
        }
        return eventMapper.toDto(event);
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
