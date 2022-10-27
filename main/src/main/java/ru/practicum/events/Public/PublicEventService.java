package ru.practicum.events.Public;

import ru.practicum.events.EventCriteria;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.ShortEventDto;

import java.util.List;

public interface PublicEventService {

    List<ShortEventDto> getAll(EventCriteria criteria);

    FullEventDto getById(Long eventId);

}
