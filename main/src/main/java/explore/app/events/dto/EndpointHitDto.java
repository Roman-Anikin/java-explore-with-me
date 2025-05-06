package explore.app.events.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EndpointHitDto {

    private String app;
    private String uri;
    private String ip;
    private String timestamp;

}
