package ru.practicum.events.admin;

import ru.practicum.events.EventCriteria;
import ru.practicum.events.dto.FullEventDto;
import ru.practicum.events.dto.NewEventDto;

import java.util.List;

public interface AdminEventService {

    List<FullEventDto> getAllByAdmin(EventCriteria criteria);

    FullEventDto updateByAdmin(Long eventId, NewEventDto eventDto);

    FullEventDto publishEvent(Long eventId);

    FullEventDto rejectEvent(Long eventId);

}
