package ru.practicum.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ViewStatsDto;
import ru.practicum.utils.CustomDateFormatter;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class EndpointHitServiceImpl implements EndpointHitService {

    private final EndpointHitMapper hitMapper;
    private final EndpointHitRepository repository;
    private final CustomDateFormatter formatter;

    public EndpointHitServiceImpl(EndpointHitMapper hitMapper,
                                  EndpointHitRepository repository, CustomDateFormatter formatter) {
        this.hitMapper = hitMapper;
        this.repository = repository;
        this.formatter = formatter;
    }

    @Override
    public void addHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = repository.save(hitMapper.fromDto(endpointHitDto));
        log.info("Был обработан запрос к эндпоинту {}", endpointHit);
    }

    @Override
    public List<ViewStatsDto> getStats(ViewStatCriteria criteria) {
        criteria.setStart(decodeDate(criteria.getStart()));
        criteria.setEnd(decodeDate(criteria.getEnd()));
        List<EndpointHit> endpointHits;
        if (criteria.getUris() != null && !criteria.isUnique()) {
            endpointHits = repository.findAllByTimestampBetweenAndUriIn(formatter.stringToDate(criteria.getStart()),
                    formatter.stringToDate(criteria.getEnd()), List.of(criteria.getUris()));
        } else if (criteria.getUris() == null && criteria.isUnique()) {
            endpointHits = repository.findAllByUniqueIp(formatter.stringToDate(criteria.getStart()),
                    formatter.stringToDate(criteria.getEnd()));
        } else if (criteria.getUris() != null && criteria.isUnique()) {
            endpointHits = repository.findAllByUrisAndUniqueIp(formatter.stringToDate(criteria.getStart()),
                    formatter.stringToDate(criteria.getEnd()), List.of(criteria.getUris()));
        } else {
            endpointHits = repository.findAllByTimestampBetween(formatter.stringToDate(criteria.getStart()),
                    formatter.stringToDate(criteria.getEnd()));
        }
        List<ViewStatsDto> viewStats = hitMapper.toDto(endpointHits);
        for (ViewStatsDto viewStat : viewStats) {
            viewStat.setHits(repository.getHits(viewStat.getUri()));
        }
        log.info("Получен список статистики {}", viewStats);
        return viewStats;
    }

    private String decodeDate(String date) {
        return URLDecoder.decode(date, StandardCharsets.UTF_8);
    }
}
