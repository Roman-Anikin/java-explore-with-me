package explore.app.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FullUserDto {

    private Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    @NotEmpty(message = "Имя не должно быть пустым")
    private String name;

    @Email
    private String email;
}
