package explore.app.events.userpublic;

import explore.app.events.*;
import explore.app.events.dto.FullEventDto;
import explore.app.events.dto.ShortEventDto;
import explore.app.events.dto.ViewStatsDto;
import explore.app.exceptions.ObjectNotFoundException;
import explore.app.exceptions.ValidationException;
import explore.app.utils.OffsetPageRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public Mono<List<ShortEventDto>> getAll(EventCriteria criteria) {
        return Mono.fromCallable(() -> {
                    checkSort(criteria.getSort());
                    Specification<Event> specification = EventSpecifications.getUserSpecifications(criteria);
                    return repository.findAll(specification, getPageable(criteria.getFrom(),
                                    criteria.getSize(), getSorting(criteria.getSort())))
                            .getContent();
                })
                .flatMap(events -> setViews(events)
                        .then(Mono.defer(() -> {
                            log.info("Получены события с просмотрами{}", events);
                            return Mono.just(eventMapper.toShortDto(events));
                        })));
    }

    @Override
    public Mono<FullEventDto> getById(Long eventId) {
        return Mono.fromCallable(() -> {
            Event event = repository.findByIdAndStateLike(eventId, EventState.PUBLISHED);
            if (event == null) {
                throw new ObjectNotFoundException("Событие не найдено");
            }
            return event;
        }).flatMap(event -> setViews(List.of(event))
                .then(Mono.fromCallable(() -> {
                    log.info("Получено событие с просмотрами {}", event);
                    return eventMapper.toDto(event);
                })));
    }

    private Mono<Void> setViews(List<Event> events) {
        if (events.isEmpty()) {
            return Mono.empty();
        }
        LocalDateTime start = events.stream()
                .map(Event::getCreatedOn)
                .min(LocalDateTime::compareTo)
                .get();

        String[] uris = events.stream()
                .map(event -> "/events/" + event.getId())
                .toArray(String[]::new);

        return client.getHits(start, LocalDateTime.now(), uris, false)
                .map(dtos -> dtos.stream().toList())
                .flatMap(hits -> {
                    for (Event event : events) {
                        for (ViewStatsDto view : hits) {
                            Long id = Long.valueOf(view.getUri().split("/")[2]);
                            if (event.getId().equals(id)) {
                                event.setViews(view.getHits());
                                break;
                            }
                        }
                    }
                    return Mono.empty();
                }).then();
    }

    private void checkSort(String sort) {
        if (sort != null && !EventSort.EVENT_DATE.toString().equals(sort)
                && !EventSort.VIEWS.toString().equals(sort)) {
            throw new ValidationException("Неверный формат сортировки");
        }
    }

    private Pageable getPageable(Integer from, Integer size, Sort sort) {
        return new OffsetPageRequest(from, size, sort);
    }

    private Sort getSorting(String sort) {
        if (sort == null) {
            return Sort.unsorted();
        }
        if (EventSort.valueOf(sort) == EventSort.EVENT_DATE) {
            return Sort.by(Sort.Direction.ASC, "eventDate");
        }
        return Sort.by(Sort.Direction.DESC, "views");
    }
}
