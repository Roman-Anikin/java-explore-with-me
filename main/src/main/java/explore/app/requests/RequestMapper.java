package explore.app.requests;

import explore.app.requests.dto.ParticipationRequestDto;
import explore.app.utils.CustomDateFormatter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestMapper {

    private final CustomDateFormatter formatter = new CustomDateFormatter();

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        return new ParticipationRequestDto(request.getId(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                formatter.dateToString(request.getCreated()),
                request.getStatus().toString());
    }

    public List<ParticipationRequestDto> toDto(List<ParticipationRequest> requests) {
        return requests
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
