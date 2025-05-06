package explore.app.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Название не должно быть пустым")
    @NotEmpty(message = "Название не должно быть пустым")
    private String name;

}
