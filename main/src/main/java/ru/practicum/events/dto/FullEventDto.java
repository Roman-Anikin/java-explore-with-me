package ru.practicum.events.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.Location;
import ru.practicum.users.dto.ShortUserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FullEventDto {

    private Long id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private ShortUserDto initiator;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    private String title;
    private int views;

}
