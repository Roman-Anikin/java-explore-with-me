package explore.app.events.dto;

import explore.app.categories.dto.CategoryDto;
import explore.app.comments.dto.CommentDto;
import explore.app.events.Location;
import explore.app.users.dto.ShortUserDto;
import lombok.*;

import java.util.List;

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
    private List<CommentDto> comments;

}
