package ru.practicum.events;

import org.springframework.stereotype.Component;
import ru.practicum.categories.Category;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.comments.CommentMapper;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.ShortEventDto;
import ru.practicum.users.dto.ShortUserDto;
import ru.practicum.utils.CustomDateFormatter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    private final static CustomDateFormatter FORMATTER = new CustomDateFormatter();
    private final static CommentMapper COMMENT_MAPPER = new CommentMapper(FORMATTER);

    public FullEventDto toDto(Event event) {
        return new FullEventDto(event.getId(),
                event.getAnnotation(),
                new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                FORMATTER.dateToString(event.getCreatedOn()),
                event.getDescription(),
                FORMATTER.dateToString(event.getEventDate()),
                new ShortUserDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                FORMATTER.dateToString(event.getPublishedOn()),
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                event.getViews(),
                COMMENT_MAPPER.toDto(event.getComments()));
    }

    public ShortEventDto toShortDto(Event event) {
        return new ShortEventDto(event.getId(),
                event.getAnnotation(),
                new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                FORMATTER.dateToString(event.getEventDate()),
                new ShortUserDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(),
                event.getTitle(),
                event.getViews(),
                COMMENT_MAPPER.toDto(event.getComments()));
    }

    public Event fromDto(NewEventDto eventDto) {
        return new Event(eventDto.getEventId(),
                eventDto.getAnnotation(),
                new Category(eventDto.getCategory(), null),
                0,
                LocalDateTime.now(),
                eventDto.getDescription(),
                FORMATTER.stringToDate(eventDto.getEventDate()),
                null,
                eventDto.getLocation(),
                eventDto.getPaid(),
                eventDto.getParticipantLimit(),
                null,
                eventDto.getRequestModeration(),
                EventState.PENDING,
                eventDto.getTitle(),
                0,
                new ArrayList<>());
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
