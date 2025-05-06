package explore.app.events.userpublic;

import explore.app.events.EventCriteria;
import explore.app.events.dto.FullEventDto;
import explore.app.events.dto.ShortEventDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PublicEventService {

    Mono<List<ShortEventDto>> getAll(EventCriteria criteria);

    Mono<FullEventDto> getById(Long eventId);

}
