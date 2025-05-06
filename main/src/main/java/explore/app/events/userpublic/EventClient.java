package explore.app.events.userpublic;

import explore.app.events.dto.EndpointHitDto;
import explore.app.events.dto.ViewStatsDto;
import explore.app.utils.CustomDateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class EventClient {

    private final WebClient client;
    private final CustomDateFormatter formatter;

    public EventClient(@Value("${explore-with-me-stat.url}") String url, CustomDateFormatter formatter) {
        client = WebClient.builder()
                .baseUrl(url)
                .build();
        this.formatter = formatter;
    }

    public void addHit(ServerWebExchange exchange) {
        client.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(makeEndpointHitDto(exchange))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(success -> log.info("Добавлено посещение в сервис статистики"))
                .doOnError(error -> log.error("Не удалось добавить посещение в сервис статистики"))
                .subscribe();
    }

    public Mono<List<ViewStatsDto>> getHits(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", encodeDate(start))
                        .queryParam("end", encodeDate(end))
                        .queryParam("uris", (Object[]) uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(ViewStatsDto.class)
                .collectList()
                .doOnSuccess(success -> log.info("Получены посещения"))
                .doOnError(error -> log.error("Не удалось получить посещения"));
    }

    private EndpointHitDto makeEndpointHitDto(ServerWebExchange exchange) {
        return new EndpointHitDto("ewm-main-service",
                exchange.getRequest().getPath().toString(),
                exchange.getRequest().getRemoteAddress().getHostString(),
                formatter.dateToString(LocalDateTime.now()));
    }

    private String encodeDate(LocalDateTime date) {
        return URLEncoder.encode(formatter.dateToString(date), StandardCharsets.UTF_8);
    }
}
