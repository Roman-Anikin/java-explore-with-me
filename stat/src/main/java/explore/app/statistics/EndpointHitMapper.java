package explore.app.statistics;

import explore.app.statistics.dto.EndpointHitDto;
import explore.app.statistics.dto.ViewStatsDto;
import explore.app.utils.CustomDateFormatter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EndpointHitMapper {

    private final CustomDateFormatter formatter = new CustomDateFormatter();

    public ViewStatsDto toDto(EndpointHit endpointHit) {
        return new ViewStatsDto(endpointHit.getApp(), endpointHit.getUri(), 0);
    }

    public EndpointHit fromDto(EndpointHitDto endpointHitDto) {
        return new EndpointHit(null,
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                formatter.stringToDate(endpointHitDto.getTimestamp()));
    }

    public List<ViewStatsDto> toDto(List<EndpointHit> endpointHits) {
        return endpointHits
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
