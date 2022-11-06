package ru.practicum.events.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.users.dto.ShortUserDto;

import java.util.List;

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
    private List<CommentDto> comments;
}
