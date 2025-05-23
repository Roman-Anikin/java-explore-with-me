package explore.app.statistics;

import explore.app.statistics.dto.EndpointHitDto;
import explore.app.statistics.dto.ViewStatsDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class EndpointHitController {

    private final EndpointHitService service;

    @PostMapping("/hit")
    public void addHit(@RequestBody EndpointHitDto endpointHitDto) {
        service.addHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getHits(ViewStatCriteria criteria) {
        return service.getStats(criteria);
    }
}
