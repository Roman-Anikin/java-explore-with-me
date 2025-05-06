package explore.app.events.admin;

import explore.app.events.EventCriteria;
import explore.app.events.dto.FullEventDto;
import explore.app.events.dto.NewEventDto;

import java.util.List;

public interface AdminEventService {

    List<FullEventDto> getAllByAdmin(EventCriteria criteria);

    FullEventDto updateByAdmin(Long eventId, NewEventDto eventDto);

    FullEventDto publishEvent(Long eventId);

    FullEventDto rejectEvent(Long eventId);

}
