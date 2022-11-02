package ru.practicum.statistics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ViewStatsDto;

import java.util.List;

@RestController
public class EndpointHitController {

    private final EndpointHitService service;

    public EndpointHitController(EndpointHitService service) {
        this.service = service;
    }

    @PostMapping("/hit")
    public void addHit(@RequestBody EndpointHitDto endpointHitDto) {
        service.addHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getHits(ViewStatCriteria criteria) {
        return service.getStats(criteria);
    }
}
