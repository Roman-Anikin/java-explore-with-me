package ru.practicum.events.dto;

import lombok.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.users.dto.ShortUserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShortEventDto {

    private Long id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String eventDate;
    private ShortUserDto initiator;
    private Boolean paid;
    private String title;
    private int views;

}
