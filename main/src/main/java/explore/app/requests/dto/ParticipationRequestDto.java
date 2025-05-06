package explore.app.requests.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParticipationRequestDto {

    private Long id;
    private Long event;
    private Long requester;
    private String created;
    private String status;

}
