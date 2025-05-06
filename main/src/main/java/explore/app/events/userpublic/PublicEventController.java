package explore.app.events.userpublic;

import explore.app.events.EventCriteria;
import explore.app.events.dto.FullEventDto;
import explore.app.events.dto.ShortEventDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/events")
public class PublicEventController {

    private final PublicEventService service;
    private final EventClient client;

    @GetMapping
    public Mono<List<ShortEventDto>> getAll(EventCriteria criteria, ServerWebExchange exchange) {
        client.addHit(exchange);
        return service.getAll(criteria);
    }

    @GetMapping("/{id}")
    public Mono<FullEventDto> getById(@PathVariable(name = "id") Long eventId, ServerWebExchange exchange) {
        client.addHit(exchange);
        return service.getById(eventId);
    }
}
