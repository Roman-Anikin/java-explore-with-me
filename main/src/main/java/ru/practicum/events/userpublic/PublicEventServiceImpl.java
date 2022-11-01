package ru.practicum.events.userpublic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.events.*;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.ShortEventDto;
import ru.practicum.events.dto.ViewStatsDto;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.utils.OffsetPageRequest;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class PublicEventServiceImpl implements PublicEventService {

    private final EventMapper eventMapper;
    private final EventRepository repository;
    private final EventClient client;

    @Override
    public List<ShortEventDto> getAll(EventCriteria criteria) {
        checkSort(criteria.getSort());
        Specification<Event> specification = EventSpecifications.getUserSpecifications(criteria);
        List<Event> events = repository.findAll(specification,
                        getPageable(criteria.getFrom(), criteria.getSize(), getSorting(criteria.getSort())))
                .getContent();
        setViews(events);
        log.info("Получен список событий {}", events);
        return eventMapper.toShortDto(events);
    }

    @Override
    public FullEventDto getById(Long eventId) {
        Event event = repository.findByIdAndStateLike(eventId, EventState.PUBLISHED);
        if (event == null) {
            throw new ObjectNotFoundException("Событие не найдено");
        }
        setViews(List.of(event));
        log.info("Получено событие {}", event);
        return eventMapper.toDto(event);
    }

    private void setViews(List<Event> events) {
        events.forEach(event -> {
            List<ViewStatsDto> views = client.getHits(event.getCreatedOn(), LocalDateTime.now(),
                            new String[]{"/events/" + event.getId()}, false)
                    .getBody();
            if (views != null && views.size() > 0) {
                event.setViews(views.get(0).getHits());
            }
        });
    }

    private void checkSort(String sort) {
        if (!EventSort.EVENT_DATE.toString().equals(sort) && !EventSort.VIEWS.toString().equals(sort)) {
            throw new ValidationException("Неверный формат сортировки");
        }
    }

    private Pageable getPageable(Integer from, Integer size, Sort sort) {
        return new OffsetPageRequest(from, size, sort);
    }

    private Sort getSorting(String sort) {
        sort = EventSort.EVENT_DATE.toString().equals(sort) ? "eventDate" : "views";
        return Sort.by(Sort.Direction.DESC, sort);
    }
}
