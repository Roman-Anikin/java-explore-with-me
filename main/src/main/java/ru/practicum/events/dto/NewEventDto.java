package ru.practicum.events.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import ru.practicum.events.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewEventDto {

    private Long eventId;

    @Size(max = 2000, min = 20)
    @NotBlank(message = "Аннотация не должна быть пустой")
    @NotEmpty(message = "Аннотация не должна быть пустой")
    private String annotation;

    private Long category;

    @Size(max = 7000, min = 20)
    @NotBlank(message = "Описание не должно быть пустым")
    @NotEmpty(message = "Описание не должно быть пустым")
    private String description;

    private String eventDate;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;

    @Size(max = 120, min = 3)
    @NotBlank(message = "Заголовок события не должнен быть пустым")
    @NotEmpty(message = "Заголовок события не должнен быть пустым")
    private String title;
}
