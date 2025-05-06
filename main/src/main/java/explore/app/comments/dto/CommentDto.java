package explore.app.comments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {

    private Long id;
    private String authorName;

    @NotBlank(message = "Комментарий не может быть пустым")
    @NotEmpty(message = "Комментарий не может быть пустым")
    @Size(max = 2000, message = "Превышена максимальная длина комментария")
    private String text;

    private String created;
    private boolean edited;
}
