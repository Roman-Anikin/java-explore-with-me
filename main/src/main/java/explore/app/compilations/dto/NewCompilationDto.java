package explore.app.compilations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewCompilationDto {

    private Long id;
    private List<Long> events;
    private boolean pinned;

    @NotBlank(message = "Название не должно быть пустым")
    @NotEmpty(message = "Название не должно быть пустым")
    private String title;

}
