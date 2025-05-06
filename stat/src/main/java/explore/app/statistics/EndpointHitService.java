package explore.app.statistics;

import explore.app.statistics.dto.EndpointHitDto;
import explore.app.statistics.dto.ViewStatsDto;

import java.util.List;

public interface EndpointHitService {

    void addHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(ViewStatCriteria criteria);
}
