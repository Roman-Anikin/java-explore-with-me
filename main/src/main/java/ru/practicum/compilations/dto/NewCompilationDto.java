package ru.practicum.compilations.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
