package ru.practicum.statistics;

import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ViewStatsDto;

import java.util.List;

public interface EndpointHitService {

    void addHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(ViewStatCriteria criteria);
}
