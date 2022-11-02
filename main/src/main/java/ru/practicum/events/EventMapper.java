package ru.practicum.events;

import org.springframework.stereotype.Component;
import ru.practicum.categories.Category;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.ShortEventDto;
import ru.practicum.users.dto.ShortUserDto;
import ru.practicum.utils.CustomDateFormatter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    private final CustomDateFormatter formatter = new CustomDateFormatter();

    public FullEventDto toDto(Event event) {
        return new FullEventDto(event.getId(),
                event.getAnnotation(),
                new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                formatter.dateToString(event.getCreatedOn()),
                event.getDescription(),
                formatter.dateToString(event.getEventDate()),
                new ShortUserDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                formatter.dateToString(event.getPublishedOn()),
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                event.getViews());
    }

    public ShortEventDto toShortDto(Event event) {
        return new ShortEventDto(event.getId(),
                event.getAnnotation(),
                new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                formatter.dateToString(event.getEventDate()),
                new ShortUserDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(),
                event.getTitle(),
                event.getViews());
    }

    public Event fromDto(NewEventDto eventDto) {
        return new Event(eventDto.getEventId(),
                eventDto.getAnnotation(),
                new Category(eventDto.getCategory(), null),
                0,
                LocalDateTime.now(),
                eventDto.getDescription(),
                formatter.stringToDate(eventDto.getEventDate()),
                null,
                eventDto.getLocation(),
                eventDto.getPaid(),
                eventDto.getParticipantLimit(),
                null,
                eventDto.getRequestModeration(),
                EventState.PENDING,
                eventDto.getTitle(),
                0);
    }

    public List<FullEventDto> toDto(List<Event> events) {
        return events
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ShortEventDto> toShortDto(List<Event> events) {
        return events
                .stream()
                .map(this::toShortDto)
                .collect(Collectors.toList());
    }
}
