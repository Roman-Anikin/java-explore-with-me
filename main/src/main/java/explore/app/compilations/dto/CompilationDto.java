package explore.app.compilations.dto;

import explore.app.events.dto.ShortEventDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompilationDto {

    private Long id;
    private List<ShortEventDto> events;
    private boolean pinned;
    private String title;

}
