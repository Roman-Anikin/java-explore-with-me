package ru.practicum.comments.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
